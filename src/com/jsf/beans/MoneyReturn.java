package com.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

//@ManagedBean
//@RequestScoped
public class MoneyReturn {
	
	private String path ;
	
	public MoneyReturn() {
		super();
	}

	public MoneyReturn(String path) {
		super();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
