package com.atguigu.atcrowdfunding.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.service.MemberService;

@RestController
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService ;
	
	
	@RequestMapping("/member/updateAuthstatus")
	public void updateAuthstatus(@RequestBody Member member) {
		memberService.updateAuthstatus(member);
	}
	
	@RequestMapping("/member/getMemberById/{memberId}")
	public Member getMemberById(@PathVariable("memberId") Integer memberId) {		
		return memberService.getMemberById(memberId);
	}
	
	@RequestMapping("/member/queryCertByMemberid/{memberId}")
	public List<Map<String, Object>> queryCertByMemberid(@PathVariable("memberId") Integer memberId){
		return memberService.queryCertByMemberid(memberId);
	}
	
	
	
	
	@RequestMapping("/member/queryMemberByPiid/{piid}")
	public Member queryMemberByPiid(@PathVariable("piid") String piid) {
		return memberService.queryMemberByPiid(piid);
	}
	
	
	@RequestMapping("/member/completeApply")
	public void completeApply(@RequestBody Member loginMember) {
		memberService.completeApply(loginMember);
	}
	
	@RequestMapping("/member/updateEmail")
	public int updateEmail(@RequestBody Member loginMember) {
		return memberService.updateEmail(loginMember);
	}
	
	@RequestMapping("/member/saveMemberCertList")
	public void saveMemberCertList(@RequestBody List<MemberCert> certList) {
		memberService.saveMemberCertList(certList);
	}
	
	
	@RequestMapping("/member/queryCertByAccttype/{accttype}")
	public List<Cert> queryCertByAccttype(@PathVariable("accttype") String accttype){		
		return memberService.queryCertByAccttype(accttype);
	}
	
	@RequestMapping("/member/updateBasicinfo")
	public int updateBasicinfo(@RequestBody Member loginMember) {
		return memberService.updateBasicinfo(loginMember);
	}
	
	
	@RequestMapping("/member/updateAccttype")
	public int updateAccttype(@RequestBody Map<String, Object> paramMap) {
		return memberService.updateAccttype(paramMap);
	}
	
	
	@RequestMapping("/member/queryTicketByMemberid/{id}")
	public Ticket queryTicketByMemberid(@PathVariable("id") Integer id) {
		return memberService.queryTicketByMemberid(id);
	}

	@RequestMapping("/member/saveTicket")
	public void saveTicket(@RequestBody Ticket ticket) {
		memberService.saveTicket(ticket);
	}
	
	
	
	@RequestMapping("/member/queryMemberByLogin/{loginacct}")
	public Member queryMemberByLogin(@PathVariable("loginacct") String loginacct) {
		
		Member member = memberService.queryMemberByLogin(loginacct);
		
		return member ;
	}
	
}
