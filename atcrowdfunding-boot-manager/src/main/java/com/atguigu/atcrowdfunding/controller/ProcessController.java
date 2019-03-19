package com.atguigu.atcrowdfunding.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.atguigu.atcrowdfunding.service.ActivitiService;
import com.atguigu.atcrowdfunding.util.Page;


@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {

	@Autowired
	private ActivitiService activitiService ;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/index")
	public String index() {
		return "process/index";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(String pdDeployId) {
		start();
		
		try {
			
			activitiService.deleteDeployment(pdDeployId);

			success(true);
		} catch (Exception e) {
			success(false);
			message("删除数据失败！");
			e.printStackTrace();
		}
		
		return end();
	}
	
	
	@RequestMapping("/view/{pdId}")
	public String view(@PathVariable("pdId") String pdId,Map<String,Object> map) {
		
		map.put("pdId", pdId);
		
		return "process/view";
	}
	
	
	
	@RequestMapping("/loadImg")
	public void loadImg(String pdId, HttpServletResponse resp) throws Exception { //流程实例id是字符串类型.
		// 通过响应对象返回图形信息
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		String url = "http://atcrowdfunding-activiti-service/activiti/loadImgById/"+pdId;
		ResponseEntity<byte[]> response = 
					restTemplate.exchange( url, HttpMethod.POST,  new HttpEntity<byte[]>(headers), byte[].class); 
		byte[] result = response.getBody();
		 
		InputStream in = new ByteArrayInputStream(result); //内存流
		OutputStream out = resp.getOutputStream(); //向浏览单端输出图片数据。
		 
		int i = -1;
		while ( (i = in.read()) != -1 ) {
			out.write(i);
		} 
	}

	
	
	@ResponseBody
	@RequestMapping("/uploadProcDef")
	public Object uploadProcDef(HttpServletRequest req) {
		start();
		
		try {
			MultipartHttpServletRequest request =(MultipartHttpServletRequest)req;
			 
			MultipartFile file = request.getFile("procDefFile");
			 
			// 获取的是表单中文件域的name属性值
			//System.out.println(file.getName());
			// 获取的是传递的文件名称
			//System.out.println(file.getOriginalFilename());
			
			
			String uuid = UUID.randomUUID().toString();
			String fileName = file.getOriginalFilename();
			final File tempFile = File.createTempFile(uuid, fileName.substring(fileName.lastIndexOf(".")));
			 
			file.transferTo(tempFile); //另存为


			
			FileSystemResource resource = new FileSystemResource(tempFile);  
			MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();  
			param.add("pdfile", resource);
			String s =	restTemplate.postForObject("http://atcrowdfunding-activiti-service/activiti/uploadProcDef",param,String.class);
			System.out.println("result string = " + s);
			
			tempFile.delete();
			
			success(true);
		} catch (Exception e) {
			success(false);
			message("加载数据失败！");
			e.printStackTrace();
		}
		
		return end();
	}
	
	
	@ResponseBody
	@RequestMapping("/queryPage")
	public Object queryPage(Integer pageno,Integer pagesize) {
		start();
		
		try {
			Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageno,pagesize);
			
			int startIndex= page.getStartindex();
			List<Map<String, Object>> queryPageList = activitiService.queryPageList(startIndex, pagesize);
			Integer totalsize = activitiService.queryPageCount();
			
			page.setDatas(queryPageList);
			page.setTotalsize(totalsize);
			
			data(page);
			
			success(true);
		} catch (Exception e) {
			success(false);
			message("加载数据失败！");
			e.printStackTrace();
		}
		
		return end();
	}
	
	
}
