package com.yzsl.util;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * JSON模型
 * 
 * 用户后台向前台返回的JSON对象
 * 
 * 
 */
public class Json implements java.io.Serializable {

	private boolean success = false;

	private String msg = "";

	private Object obj = null;
	
	//EXT 静态树
	private List children = Lists.newArrayList();
	
	

	public List getChildren() {
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
