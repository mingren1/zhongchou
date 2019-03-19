package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
	
	ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<Map<String,Object>>();
	
	public void start() {
		Map<String,Object> result = new HashMap<String,Object>();
		threadLocal.set(result);
	}
	
	
	public Map<String,Object> end() {
		return threadLocal.get() ;
	}
	
	
	public void success(boolean success) {
		threadLocal.get().put("success",success);
	}
	
	public void message(String message) {
		threadLocal.get().put("message",message);
	}
	
	public void data(Object data) {
		threadLocal.get().put("data",data);
	}
	
}
