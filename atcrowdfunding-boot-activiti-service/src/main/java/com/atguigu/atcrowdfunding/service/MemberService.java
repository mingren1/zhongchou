package com.atguigu.atcrowdfunding.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.bean.Member;

@FeignClient("atcrowdfunding-member-service")
public interface MemberService {

	@RequestMapping("/member/queryMemberByPiid/{piid}")
	public Member queryMemberByPiid(@PathVariable("piid") String piid);

	@RequestMapping("/member/updateAuthstatus")
	public void updateAuthstatus(@RequestBody Member member);

}
