package com.atguigu.atcrowdfunding.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.atguigu.atcrowdfunding.util.Const;

/**
 * ServletContextListener监听ServletContext对象的创建和销毁：
 * 	ServletContext对象在服务器启动时加载项目时来创建这个对象。
 *  ServletContext对象在服务器关闭，或卸载项目时销毁这个对象。
 * @author zhangyu
 * 
 */
@WebListener
public class StartUpSystemListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//1.在父亲启动过程中，将当前项目上下文路径存放到application域中，给页面使用
		
		ServletContext application = sce.getServletContext();
		
		String contextPath = application.getContextPath();
		
		application.setAttribute(Const.APP_PATH, contextPath);
		
		System.out.println("StartUpSystemListener - contextInitialized ...");
	}



}
