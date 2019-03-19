package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("atcrowdfunding-activiti-service")
public interface ActivitiService {

	@RequestMapping("/activiti/queryPageCount")
	public Integer queryPageCount() ;
	
	
	@RequestMapping("/activiti/queryPageList/{startIndex}/{pagesize}")
	public List<Map<String, Object>> queryPageList(
					@PathVariable("startIndex") Integer startIndex,
					@PathVariable("pagesize") Integer pagesize) ;
	
	
	@RequestMapping("/activiti/deleteDeployment/{pdDeployId}")
	public void deleteDeployment(@PathVariable("pdDeployId") String pdDeployId) ;


	@RequestMapping("/activiti/queryAuthPageList")
	public List<Map<String, Object>> queryAuthPageList(@RequestBody Map<String, Object> paramMap);

	@RequestMapping("/activiti/queryAuthPageCount")
	public Integer queryAuthPageCount(@RequestBody Map<String, Object> paramMap);

	@RequestMapping("/activiti/passApply/{taskId}/{memberId}")
	public void passApply(@PathVariable("taskId") String taskId,
						  @PathVariable("memberId")  Integer memberId);
	
}
