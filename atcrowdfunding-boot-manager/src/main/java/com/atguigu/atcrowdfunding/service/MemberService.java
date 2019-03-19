package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.bean.Member;

@FeignClient("atcrowdfunding-member-service")
public interface MemberService {

	@RequestMapping("/member/getMemberById/{memberId}")
	Member getMemberById(@PathVariable("memberId") Integer memberId);
	
	@RequestMapping("/member/queryCertByMemberid/{memberId}")
	List<Map<String, Object>> queryCertByMemberid(@PathVariable("memberId") Integer memberId);

}
