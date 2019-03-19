package com.atguigu.atcrowdfunding.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.MemberCert;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.service.ActivitiService;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Datas;

@Controller
public class MemberController extends BaseController {

	@Autowired
	private MemberService memberService ;
	
	@Autowired
	private ActivitiService activitiService ;
	
	
	@ResponseBody
	@RequestMapping("/member/completeApply")
	public Object completeApply(String authcode,HttpSession session) {
		start();
		try {
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setAuthstatus("1");
			
			Ticket ticket = memberService.queryTicketByMemberid(loginMember.getId());
			
			if(ticket.getAuthcode().equals(authcode)) {
				memberService.completeApply(loginMember);
				success(true);
			}else {
				loginMember.setAuthstatus("0");
				success(false);
			}
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/member/updateEmail")
	public Object updateEmail(String email,HttpSession session) {
		start();
		try {
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setEmail(email);
			
			int i = memberService.updateEmail(loginMember);
			
			success(i==1);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/member/uploadCertFile")
	public Object uploadCertFile(Datas ds,HttpSession session) {
		start();
		try {
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			
			List<MemberCert> certList = ds.getCertList();
			for (MemberCert memberCert : certList) {
				MultipartFile certfile = memberCert.getCertfile();
				
				String originalFilename = certfile.getOriginalFilename();
				
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				
				String filename = UUID.randomUUID().toString() + ext ; //重新生成新文件名称
				
				String filepath = "D:/resources/atcrowdfunding/pics/cert/" + filename ;
				
				certfile.transferTo(new File(filepath));
				
				
				memberCert.setMemberid(loginMember.getId());
				memberCert.setIconpath(filename);
				memberCert.setCertfile(null); //图片已经存储到服务器了，就不需要传递给远程服务。
			}
			
			memberService.saveMemberCertList(certList);
			
			success(true);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/member/updateBasicinfo")
	public Object updateBasicinfo(Member member,HttpSession session) {
		start();
		try {
			Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			loginMember.setRealname(member.getRealname());
			loginMember.setCardnum(member.getCardnum());
			loginMember.setTel(member.getTel());

			int i = memberService.updateBasicinfo(loginMember);
			success(i==1);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	@ResponseBody
	@RequestMapping("/member/updateAccttype")
	public Object updateAccttype(String accttype,HttpSession session) {
		start();
		try {
			Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
			member.setAccttype(accttype);
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("memberId", member.getId());
			paramMap.put("accttype", accttype);
			paramMap.put("loginacct", member.getLoginacct());
			
			int i = memberService.updateAccttype(paramMap);
			success(i==1);
		} catch (Exception e) {
			success(false);
			e.printStackTrace();
		}
		
		return end();
	}
	
	
	// 开始申请
	@RequestMapping("/member/apply")
	public String apply(HttpSession session,Map<String,Object> map) {
		// 根据会员查询流程审批单：（memberid=1,status=0）
		Member member = (Member)session.getAttribute(Const.LOGIN_MEMBER);
		
		Ticket ticket = memberService.queryTicketByMemberid(member.getId());
		if(ticket==null) {
			// 如果流程审批单不存在：说明第一次刚刚开始申请
			// 1.启动流程实例
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("loginacct", member.getLoginacct());
			String piid = activitiService.startProcessInstance(paramMap);
			
			
			// 2.保存流程单
			ticket = new Ticket();
			ticket.setMemberid(member.getId());
			ticket.setPiid(piid);
			ticket.setStatus("0");
			ticket.setPstep("accttype");
			
			memberService.saveTicket(ticket);
			// 3.跳转页面
			
			return "member/accttype";
		}else {
			// 如果流程审批单存在：说明之前申请过流程
			// 3.继续上一个流程进行申请
			// 根据流程单的步骤跳转到相应的页面
			String pstep = ticket.getPstep();
			if ( "accttype".equals(pstep) ) {
				return "member/accttype";
			} else if ( "basicinfo".equals(pstep) ) {
				return "member/basicinfo";
			} else if ( "certupload".equals(pstep) ) {
				
				List<Cert> certList = memberService.queryCertByAccttype(member.getAccttype());
				map.put("certList", certList);
				
				return "member/certupload";
			} else if ( "checkemail".equals(pstep) ) {
				return "member/checkemail";
			} else if ( "checkauthcode".equals(pstep) ) {
				return "member/checkauthcode";
			}

		}
		return "member/accttype";
	}

}
