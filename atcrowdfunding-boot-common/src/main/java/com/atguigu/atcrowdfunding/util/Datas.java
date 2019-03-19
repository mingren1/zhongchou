package com.atguigu.atcrowdfunding.util;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.atcrowdfunding.bean.MemberCert;

public class Datas {


	private List<Integer> ids = new ArrayList<Integer>();
	
	/*private List<User> userList = new ArrayList<User>();*/
	
	
	private List<MemberCert> certList = new ArrayList<MemberCert>();

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<MemberCert> getCertList() {
		return certList;
	}

	public void setCertList(List<MemberCert> certList) {
		this.certList = certList;
	}
	
	

}
