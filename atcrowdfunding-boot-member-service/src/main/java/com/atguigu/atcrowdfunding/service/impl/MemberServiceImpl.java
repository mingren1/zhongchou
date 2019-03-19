package com.atguigu.atcrowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.dao.MemberDao;
import com.atguigu.atcrowdfunding.service.ActivitiService;
import com.atguigu.atcrowdfunding.service.MemberService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private ActivitiService activitiService ;

	@Override
	public Member queryMemberByLogin(String loginacct) {
		return memberDao.queryMemberByLogin(loginacct);
	}

	@Override
	public Ticket queryTicketByMemberid(Integer id) {
		return memberDao.queryTicketByMemberid(id);
	}

	@Override
	public void saveTicket(Ticket ticket) {
		memberDao.saveTicket(ticket);
	}

	@Override
	public int updateAccttype(Map<String, Object> paramMap) {
		// 1.修改会员账户类型字段:accttype
		int i = memberDao.updateAccttype(paramMap);
		
		// 2.修改流程单的步骤：basicinfo
		Integer memberId = (Integer)paramMap.get("memberId");
		
		Ticket ticket = memberDao.queryTicketByMemberid(memberId) ;
		ticket.setPstep("basicinfo");
		
		memberDao.updateTicketPstep(ticket);
		
		// 3.流程需要往下执行一步:
		// 根据流程实例id和委托人loginacct可以查询具体任务对象。
		// 传流程变量:loginacct
		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("loginacct", paramMap.get("loginacct"));
		variables.put("piid", ticket.getPiid());
		
		activitiService.completeTask(variables);
		return i;
	}

	@Override
	public int updateBasicinfo(Member loginMember) {
		// 1.修改会员基本信息字段:realname,cardnum,tel
		int i = memberDao.updateBasicinfo(loginMember);
		
		// 2.修改流程单的步骤：certupload
		Ticket ticket = memberDao.queryTicketByMemberid(loginMember.getId()) ;
		ticket.setPstep("certupload");
		
		memberDao.updateTicketPstep(ticket);
		
		// 3.流程需要往下执行一步:
		// 根据流程实例id和委托人loginacct可以查询具体任务对象。
		// 传流程变量:loginacct		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("loginacct", loginMember.getLoginacct());
		variables.put("piid", ticket.getPiid());
		variables.put("flag", true);
		
		activitiService.completeTask(variables);
		return i;
	}

	@Override
	public List<Cert> queryCertByAccttype(String accttype) {
		return memberDao.queryCertByAccttype(accttype);
	}

	@Override
	public void saveMemberCertList(List<MemberCert> certList) {
		// 1.保存会员和资质的关系数据
		memberDao.saveMemberCertList(certList);
		
		// 2.修改流程单的步骤：checkemail
		MemberCert mc = certList.get(0);
		Integer memberid = mc.getMemberid();
		Member member = memberDao.getMemberById(memberid);
		Ticket ticket = memberDao.queryTicketByMemberid(memberid) ;
		ticket.setPstep("checkemail");
		
		memberDao.updateTicketPstep(ticket);
		
		// 3.流程需要往下执行一步:
		// 根据流程实例id和委托人loginacct可以查询具体任务对象。		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("loginacct", member.getLoginacct());
		variables.put("piid", ticket.getPiid());
		variables.put("flag", true);
		
		activitiService.completeTask(variables);
	}

	@Override
	public int updateEmail(Member loginMember) {
		// 1.修改邮箱
		int count = memberDao.updateEmail(loginMember);
		
		// 2.修改流程单的步骤：checkauthcode		
		Ticket ticket = memberDao.queryTicketByMemberid(loginMember.getId()) ;
		ticket.setPstep("checkauthcode");
		
		StringBuilder str = new StringBuilder();
		for(int i=1; i<=4; i++) {
			str.append(new Random().nextInt(10));
		}
		ticket.setAuthcode(str.toString());
		
		memberDao.updateTicketPstepAndAuthcode(ticket);
		
		// 3.流程需要往下执行一步:
		// 根据流程实例id和委托人loginacct可以查询具体任务对象。		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("loginacct", loginMember.getLoginacct());
		variables.put("piid", ticket.getPiid());
		variables.put("flag", true);
		variables.put("email", loginMember.getEmail());
		variables.put("authcode", str.toString());
		
		activitiService.completeTask(variables);
		return count;
	}

	@Override
	public void completeApply(Member loginMember) {
		// 1.修改会员实名认证状态  0 -> 1
		memberDao.updateAuthstatus(loginMember);
		
		// 2.修改流程单的状态: 0-> 1		
		Ticket ticket = memberDao.queryTicketByMemberid(loginMember.getId()) ;
		ticket.setStatus("1");
		
		memberDao.updateTicketStatus(ticket);
		
		// 3.流程需要往下执行一步:
		// 根据流程实例id和委托人loginacct可以查询具体任务对象。		
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("loginacct", loginMember.getLoginacct());
		variables.put("piid", ticket.getPiid());
		
		activitiService.completeTask(variables);
	}

	@Override
	public Member queryMemberByPiid(String piid) {
		return memberDao.queryMemberByPiid(piid);
	}

	@Override
	public Member getMemberById(Integer memberId) {
		return memberDao.getMemberById(memberId);
	}

	@Override
	public List<Map<String, Object>> queryCertByMemberid(Integer memberId) {
		return memberDao.queryCertByMemberid(memberId);
	}

	@Override
	public void updateAuthstatus(Member member) {
		memberDao.updateAuthstatus(member);
	}

}
