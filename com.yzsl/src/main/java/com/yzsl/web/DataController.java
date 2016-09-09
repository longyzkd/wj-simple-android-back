/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.yzsl.bean.Data;
import com.yzsl.bean.User;
import com.yzsl.service.DataService;
import com.yzsl.util.Json;


/**
 * 
 * mobile apis
 * 
 * @author wj
 */
@Controller
@RequestMapping(value = "/data")
public class DataController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(DataController.class);
	

	@Autowired
	private DataService service;


	@RequestMapping(value="get" ,method = RequestMethod.GET)
	public @ResponseBody  Object list() {

		return service.getSqData();
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value="login" )
	public void login(String username,String password,HttpServletResponse response) {
		logger.info("username---"+username);
		logger.info("password---"+password);
		
//		String sql = "SELECT UserID, UserName, Password, UserType, IsStoped, Note, Phone,AvatarPath "+
//					" FROM tmanageruser a WHERE a.IsStoped=0 AND (a.UserID='{0}' OR a.Phone='{0}') "+
//					"AND a.Password='{1}'";
//		sql = StringUtil.formateString(sql, userId, MD5Util.md5(password));
//		List<Tmanageruser> users = manageruserService.findListBySql(sql,new Tmanageruser());
//		Tmanageruser user=null;
//		if(users != null && users.size()>0){
//			user = users.get(0);
//		}
		User user = null;
		if("wj".equals(username) && "123".equals(password)){
			
			 user = new User();
			 user.setUsername("wj");
			 user.setPassword("123");
		}
		Json json = new Json();
		json.setSuccess(true);
		if (user != null) {
			json.setObj(user);
			
		} else {
			json.setMsg("用户名或密码错误！");
			json.setSuccess(false);
		}
		writeJson(response,json);
		
		
	}
	
	@RequestMapping(value="doNotNeedSessionAndSecurity_GetData" )
	public void doNotNeedSessionAndSecurity_GetData(String service_code,String userId,String password,Date dateFrom,Date dateTo,String selectType,Integer page,Integer count,HttpServletRequest request,HttpServletResponse response){
		Json json = new Json();
		logger.debug("------------conditions---------------");
		logger.debug("service_code:  "+service_code);
		logger.debug("userId:  "+userId);
		logger.debug("password:  "+password);
		
		logger.debug("dateFrom:  "+dateFrom);
		logger.debug("dateTo:  "+dateTo);
		logger.debug("selectType:  "+selectType);
		logger.debug("page:  "+page);
		logger.debug("count:  "+count);
		
		logger.debug("------------// conditions---------------");
		try {
			
			if (StringUtils.isBlank(service_code)) {
				json.setMsg("服务编号不能为空");
				writeJson(response,json);
				return;
			}
		
				//水情数据
				 if(service_code.equals("sqData")){
					 sqData(response);
				}else if(service_code.equals("yzzData")){//扬州闸基本数据
					yzzData(response);
				}else if(service_code.equals("yzzDynamicData")){//扬州闸动态数据
					yzzDynamicData(dateFrom,dateTo,selectType,page,count,response);
				}else if(service_code.equals("yzzSwData")){//扬州闸水位数据
					yzzSwData(response);
				}else if(service_code.equals("yzzKdData")){//扬州闸开度数据
					yzzKdData(response);
				}else if(service_code.equals("yzzDlData")){//扬州闸电压数据
					yzzDlData(response);
				}
				
				
				
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("错误:"+e.getMessage());
			logger.error(e.getMessage());
			writeJson(response,json);
		}
		
	}

	private void yzzKdData(HttpServletResponse response) {
		logger.info("查询扬州闸开度最新数据 start");
		Map<String,Object> map = service.getYzzKdData();
		writeJson(response,map);
		logger.info("查询扬州闸开度最新数据 end");
	}

	private void yzzSwData(HttpServletResponse response) {
		logger.info("查询扬州闸水位最新数据 start");
		Map<String,Object> map = service.getYzzSwData();
		writeJson(response,map);
		logger.info("查询扬州闸水位最新数据 end");
	}

	private void yzzDlData(HttpServletResponse response) {
		logger.info("查询扬州闸电压最新数据 start");
		Map<String,Object> map = service.getYzzDlData();
		writeJson(response,map);
		logger.info("查询扬州闸电压最新数据 end");
	}

	private void yzzDynamicData(Date dateFrom, Date dateTo, String selectType,Integer page,Integer count,HttpServletResponse response) {
		
		
		List<Map> list = Lists.newArrayList();
		try {
			list = service.getYzzDynamicData(dateFrom,dateTo,selectType,page,count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		writeJson(response,list);
		
	}

	private void yzzData(HttpServletResponse response) {
		logger.info("查询扬州闸最新数据 start");
		Map<String,Object> map = service.getYzzData();
		logger.info("查询扬州闸最新数据 end");
		writeJson(response,map);
		
	}

	private void sqData(HttpServletResponse response) {
//		service.syncData(); // 只在这同步了
		
		logger.info("查询水情最新数据 start");
		List<Data> list = service.getSqData();
		logger.info("查询水情最新数据 end");
		Json json = new Json();
		json.setSuccess(true);
		json.setObj(list);
//		writeJson(response,list);
		writeJson(response,json);
		
	}

	
	
	
}

