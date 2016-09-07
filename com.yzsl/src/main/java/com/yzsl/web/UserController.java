/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzsl.bean.User;
import com.yzsl.service.account.AccountService;
import com.yzsl.util.Json;


/**
 * 
 * @author wj
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	

	@Autowired
	private AccountService service;

	
	
	/**
	 * form提交
	 * @param user
	 * @return
	 */
	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
	public  void updatePwd( String password ,HttpServletRequest request, HttpServletResponse response, HttpSession session)  {
		Json json = new Json();
		try{
			
			User current = getCurrentUser();
			current.setPassword(password);
			
			service.updatePwd(current);
			
			json.setSuccess(true);
			json.setMsg("保存成功");
			writeJson(response,json);
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			
			json.setSuccess(false);
			json.setMsg("保存失败");
			writeJson(response,json);
		}
	}
	
	
	
}

