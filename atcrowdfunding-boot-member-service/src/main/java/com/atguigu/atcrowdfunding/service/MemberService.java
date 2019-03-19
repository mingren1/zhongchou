package com.atguigu.atcrowdfunding.service;

import java.util.List;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface MemberService {

	Member queryMemberByLogin(String loginacct);

	Ticket queryTicketByMemberid(Integer id);

	void saveTicket(Ticket ticket);

	int updateAccttype(Map<String, Object> paramMap);

	int updateBasicinfo(Member loginMember);

	List<Cert> queryCertByAccttype(String accttype);

	void saveMemberCertList(List<MemberCert> certList);

	int updateEmail(Member loginMember);

	void completeApply(Member loginMember);

	Member queryMemberByPiid(String piid);

	Member getMemberById(Integer memberId);

	List<Map<String, Object>> queryCertByMemberid(Integer memberId);

	void updateAuthstatus(Member member);

}
