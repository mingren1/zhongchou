package com.atguigu.atcrowdfunding.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.service.MemberService;
import com.atguigu.atcrowdfunding.util.ApplicationContextUtils;

//实名认证审批通过的事件处理
public class PassListener implements ExecutionListener {

	//1.流程监听器不能直接依赖注入service,因为流程监听器是由底层activiti框架管理的。
	//@Autowired
	//private MemberService memberService ; 
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		//修改实名认证申请状态  1->2 
		
		//2.不要自己创建IOC容器。否则，容器对象就不是单例的。
		//ApplicationContext ioc = new ClassPathXmlApplicationContext("") ;
		
		ApplicationContext ioc = ApplicationContextUtils.applicationContext;
		MemberService memberService = ioc.getBean(MemberService.class);
		
		Integer memberId = (Integer)execution.getVariable("memberId");
		
		Member member = new Member();
		member.setId(memberId);
		member.setAuthstatus("2");
		
		memberService.updateAuthstatus(member);
		
	}

}
