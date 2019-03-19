package com.atguigu.atcrowdfunding.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.util.Const;

@Controller
public class PortalController extends BaseController {

	@Autowired
	private MemberService memberService ;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("/member")
	public String member() {
		return "member";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		if(session!=null) {
			session.removeAttribute(Const.LOGIN_MEMBER);
			session.invalidate();
		}		
		return "redirect:/toLogin";
	}
	
	
	
	@ResponseBody
	@RequestMapping("/doLogin")
	public Object doLogin(String loginacct,String userpswd,String type,HttpSession session) {
		start();
		try {
			
			Member member = memberService.queryMemberByLogin(loginacct);
			
			if(member == null) {				
				message(Const.LOGIN_LOGINACCT_ERROR);
				success(false);
			}else {
				if(!member.getUserpswd().equals(userpswd)) {
					message(Const.LOGIN_USERPSWD_ERROR);
					success(false);
				}else {
					
					session.setAttribute(Const.LOGIN_MEMBER, member);
					
					success(true);
				}
			}
		} catch (Exception e) {
			success(false);
			message(e.getMessage());
			e.printStackTrace();
		}
		return end();
	}
	
	
}
