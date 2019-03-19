package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.service.ActivitiService;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.util.Page;

@Controller
//实名认证审核模块
public class AuthController extends BaseController {

	@Autowired
	private ActivitiService activitiService ;
	
	@Autowired
	private MemberService memberService ;
	
	@RequestMapping("/auth/index")
	public String index() {
		return "auth/index";
	}
	
	@ResponseBody
	@RequestMapping("/auth/pass")
	public Object pass(String taskId,Integer memberId) {
		start();
		
		try {
			
			activitiService.passApply(taskId,memberId);
			
			success(true);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	
	@RequestMapping("/auth/view/{memberId}/{taskId}")
	public String index(@PathVariable("memberId") Integer memberId,
						@PathVariable("taskId") String taskId,
						Map<String,Object> map) {
		
		Member member = memberService.getMemberById(memberId);		
		List<Map<String,Object>> certList = memberService.queryCertByMemberid(memberId);
		
		map.put("member", member);
		map.put("certList", certList);
		map.put("memberId", memberId);
		map.put("taskId", taskId);
		
		return "auth/view";
	}
	
	
	@ResponseBody
	@RequestMapping("/auth/queryPage")
	public Object queryPage(Integer pageno,Integer pagesize) {
		start();
		
		try {
			Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageno,pagesize);
			
			int startIndex= page.getStartindex();
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("pageno", pageno);
			paramMap.put("pagesize", pagesize);
			paramMap.put("startIndex", startIndex);
			
			List<Map<String, Object>> queryPageList = activitiService.queryAuthPageList(paramMap);
			Integer totalsize = activitiService.queryAuthPageCount(paramMap);
			
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
