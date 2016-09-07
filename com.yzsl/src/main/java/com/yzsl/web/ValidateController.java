/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzsl.bean.User;
import com.yzsl.service.account.AccountService;
import com.yzsl.util.Digests;
import com.yzsl.util.Encodes;
import com.yzsl.util.Json;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/validate")
public class ValidateController extends CommonController{
	private static Logger logger = LoggerFactory.getLogger(ValidateController.class);

	@Autowired
	private AccountService service;
	
	/**
	 * 
	 * @param beanClazz
	 * @param property
	 * @param val 参数暂仅支持String
	 * @param action 新增还是修改
	 * @return
	 */
	@RequestMapping(value="checkpwd",method = RequestMethod.POST)
	public  void checkpwd(String val,HttpServletResponse response)  {
		Json json = new Json();
		try{
			
			User u = getCurrentUser();
//			User u = new User();
//			u.setPassword("2d27b0a003da078b848cb29d98f07fd8e8cff0ec");
//			u.setSalt("5a4911aafc1f2e3a");
			byte[] hashPassword = Digests.sha1(val.getBytes(), Encodes.decodeHex(u.getSalt()), AccountService.HASH_INTERATIONS);
			String encrypted = Encodes.encodeHex(hashPassword);
		
			json.setSuccess(u.getPassword().equals(encrypted));
			
			writeJson(response,json);
			
			
		} catch (Exception e) {//TODO 做成过滤器
			logger.error(e.getMessage());
			e.printStackTrace();
			json.setMsg("系统错误");
			writeJson(response,json);
		}
	}
}
