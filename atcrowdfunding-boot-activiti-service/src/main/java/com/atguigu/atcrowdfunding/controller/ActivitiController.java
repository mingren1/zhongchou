package com.atguigu.atcrowdfunding.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.service.MemberService;

@RestController
public class ActivitiController extends BaseController {
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService ;
	
	@Autowired
	private TaskService taskService ;
	
	@Autowired
	private MemberService memberService ;
	
	@RequestMapping("/activiti/passApply/{taskId}/{memberId}")
	public void passApply(@PathVariable("taskId") String taskId,
						  @PathVariable("memberId")  Integer memberId) {
		
		Map<String,Object> variables = new HashMap<String,Object>();		
		variables.put("flag", true);
		variables.put("memberId", memberId); //可以给流程监听器使用的。
		
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		taskService.complete(task.getId(),variables);
	}
	
	@RequestMapping("/activiti/queryAuthPageList")
	public List<Map<String, Object>> queryAuthPageList(@RequestBody Map<String, Object> paramMap){
		List<Task> listPage = taskService
				.createTaskQuery()
				.processDefinitionKey("authflow")
				.taskCandidateGroup("backcheck")
				.listPage((Integer)paramMap.get("startIndex"), (Integer)paramMap.get("pagesize"));
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (Task task : listPage) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			String processDefinitionId = task.getProcessDefinitionId();
			ProcessDefinition pd = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId)
				.singleResult();
			
			map.put("pdName", pd.getName());
			map.put("pdVersion", pd.getVersion());
			map.put("pdKey", pd.getKey());
			map.put("pdId", pd.getId());
			
			map.put("taskName", task.getName());
			map.put("taskId", task.getId());
			
			String piid = task.getProcessInstanceId();
			
			Member member = memberService.queryMemberByPiid(piid);
			
			map.put("username", member.getUsername());
			map.put("loginacct", member.getLoginacct());
			map.put("memberId", member.getId());
			
			list.add(map);
		}
		
		return list ;
	}

	@RequestMapping("/activiti/queryAuthPageCount")
	public Integer queryAuthPageCount(@RequestBody Map<String, Object> paramMap) {
		Long count =  taskService
			.createTaskQuery()
			.processDefinitionKey("authflow")
			.taskCandidateGroup("backcheck")
			.count();
		return count.intValue();
	}

	@RequestMapping("/activiti/completeTask")
	public void completeTask(@RequestBody Map<String, Object> variables) {
		Task task = taskService.createTaskQuery()
				.processInstanceId((String)variables.get("piid"))
				.taskAssignee((String)variables.get("loginacct"))
				.singleResult();
		taskService.complete(task.getId(), variables);
	}
	
	@RequestMapping("/activiti/startProcessInstance")
	public String startProcessInstance(@RequestBody Map<String,Object> paramMap) {
		
		ProcessDefinition processDefinition = repositoryService
			.createProcessDefinitionQuery()
			.processDefinitionKey("authflow")
			.latestVersion()
			.singleResult();
		
		ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId(),paramMap);
		
		return pi.getId();
	}
	
	
	@RequestMapping("/activiti/deleteDeployment/{pdDeployId}")
	public void deleteDeployment(@PathVariable("pdDeployId") String pdDeployId) {
		repositoryService.deleteDeployment(pdDeployId, true); //true表示级联删除。
	}
	
	
	@RequestMapping("/activiti/loadImgById/{pdId}")
	public byte[] loadImgById(@PathVariable("pdId")  String pdId) {
	 	// 部署ID ==>  流程定义ID
		// 从数据库中读取流程定义的图片
		//根据流程部署id和部署资源名称获取部署图片的输入流。
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinition pd = query.processDefinitionId(pdId).singleResult();
		
		InputStream in =  repositoryService.getResourceAsStream(pd.getDeploymentId(),pd.getDiagramResourceName());
		 
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  //内存流
		byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据 
		int rc = 0; 
		try {
			while ((rc = in.read(buff, 0, 100)) > 0) { 
				swapStream.write(buff, 0, rc); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
		 
		return in_b;
	}

	
	
	@RequestMapping("/activiti/uploadProcDef")
	public String uploadProcDef(@RequestParam("pdfile") MultipartFile file) {
		
		try {
			//repositoryService.createDeployment().addClasspathResource("").deploy();
			repositoryService
					.createDeployment()
					.addInputStream(file.getOriginalFilename(), file.getInputStream())
					.deploy();
			return "部署成功!";
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "部署失败!";
	}
	
	
	
	
	
	@RequestMapping("/activiti/queryPageCount")
	public Integer queryPageCount() {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		int count = (int) query.count();
		return count;
	}
	
	
	//Could not write JSON: Direct self-reference leading to cycle
	@RequestMapping("/activiti/queryPageList/{startIndex}/{pagesize}")
	public List<Map<String, Object>> queryPageList(@PathVariable("startIndex") Integer startIndex,@PathVariable("pagesize") Integer pagesize) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> listPage = query.listPage(startIndex, pagesize);
		
		List<Map<String, Object>> pdMapList = new ArrayList<Map<String,Object>>();
		 
		for ( ProcessDefinition pd : listPage ) {
			Map<String, Object> pdMap = new HashMap<String, Object>();
			pdMap.put("pdId", pd.getId());
			pdMap.put("pdKey", pd.getKey());
			pdMap.put("pdName", pd.getName());
			pdMap.put("pdVersion", pd.getVersion());
			pdMap.put("pdDeployId", pd.getDeploymentId());
			pdMapList.add(pdMap);
		}
		 
		return pdMapList;
	}

}
