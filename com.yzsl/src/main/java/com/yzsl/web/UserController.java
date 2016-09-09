/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yzsl.bean.Menu;
import com.yzsl.bean.MenuData;
import com.yzsl.bean.User;
import com.yzsl.service.account.AccountService;
import com.yzsl.util.Json;
import com.yzsl.util.Page;


/**
 * 
 * @author wj
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends CommonController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	public static final String ADMIN = "admin";

	@Autowired
	private AccountService service;

	
	/**
	 * form提交
	 * @param user
	 * @return
	 */
	@RequestMapping(value="updatePwd",method = RequestMethod.POST)
	public  void updatePwd(String userId, String password ,HttpServletRequest request, HttpServletResponse response, HttpSession session)  {
		Json json = new Json();
		try{
			if(StringUtils.isBlank(userId)){
				
				User current = getCurrentUser();
				current.setPassword(password);
				
				service.updatePwd(current);
			}else{
				User current = service.findUserByLoginName(userId);
				current.setPassword(password);
				
				service.updatePwd(current);
				
			}
		
			
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
	
	
	@RequestMapping(value="getMainMenu")
	public void getMainMenu(HttpServletResponse response){
		User u = getCurrentUser();
		List<MenuData> menuTree = new ArrayList<MenuData>();
		if(u != null)
		{
			String userIdString = u.getUserId();
			List<Menu> menus ;
			
			if(userIdString.equals(ADMIN) ){//查询所有菜单
				menus = service.findAllMenus();
			}else{
				menus = service.findMenuByuserId(userIdString);
			}
			menuTree = getMenuTree(menus);
		}
		
		writeJson(response,menuTree);
	}
	@RequestMapping(value="list")
	public String userList(HttpServletResponse response){
		return "account/tmanageruser"; 
	}
	@RequestMapping(value="toEdit")
	public String toEdit(String userId,String action,HttpServletResponse response,HttpServletRequest request,Model model){
		model.addAttribute("action", action);
		model.addAttribute("userId", userId);//null的话 前台是空串
		return "account/tmanageruserrEidt"; 
	}
	
	
	/**
	 * 新建一个用户 
	 */
	@RequestMapping(value="saveUser")
	public void saveUser(User data,String[] funidvalue,HttpServletResponse response) {
		Json json = new Json();
		try {
			service.saveUser(data,funidvalue);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}
		
		writeJson(response,json);
		
	}
	
	/**
	 * 更新一个用户
	 */
	@RequestMapping(value="updateUser")
	public void updateUser(User data,String[] funidvalue,HttpServletResponse response) {
		Json json = new Json();
		try {
			service.updateUser(data,funidvalue);
			json.setSuccess(true);
			json.setMsg("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}
		
		writeJson(response,json);
	}
	
	/**
	 * 查找分页后的grid
	 */
	@RequestMapping(value="listData")
	public void listData(Page<User> page,User user,HttpServletResponse response) {
		page = service.getUsers(page,user);
		writeJson(response,page);
	}
	/**
	 * 查找分页后的grid
	 */
	@RequestMapping(value="one")
	public void one(User user,HttpServletResponse response) {
		User u  = service.one(user);
		writeJson(response,u);
	}
	/**
	 * 删除用户
	 */
	@RequestMapping(value="delete")
	public void delete(User user,HttpServletResponse response) {
		Json json = new Json();
		
		try{
		
			service.del(user);
			json.setSuccess(true);
			json.setMsg("删除成功");
		}catch(Exception e){
			json.setMsg("系统出错");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		writeJson(response,json);
	}
	
	
	public List<MenuData> getMenuTree(List<Menu> menus){
		List<MenuData> menuDatas= new ArrayList<MenuData>();
		String path = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath()+"/";
		for(Menu menu1 : menus){
			if(StringUtils.isBlank(menu1.getFmenuId())){ //组装menuData,只有两层菜单
				MenuData menuData = new MenuData();
				menuData.setId(menu1.getMenuId()+"|"+menu1.getFunUrl());
				menuData.setTitle(menu1.getMenuName());
				menuData.setIconCls(menu1.getIconUrl());
				if(menuData.getChildren() == null){
					menuData.setChildren(new ArrayList<MenuData>());
				}
				List<MenuData> menuDatas1= new ArrayList<MenuData>();
				for(Menu menu : menus){
					if(menu.getFmenuId().equals(menu1.getMenuId())){
						MenuData menuData1 = new MenuData();
						menuData1.setFMenuId(menu.getFmenuId());
						menuData1.setId(menu.getMenuId()+"|"+menu.getFunUrl());
						menuData1.setText(menu.getMenuName());
						menuData1.setIcon(path + menu.getIconUrl());
						menuData1.setExpanded(false);
						menuData1.setLeaf(true);
						menuDatas1.add(menuData1);
					}
				}
				menuData.setChildren(menuDatas1);
				menuDatas.add(menuData);
			}
		}
		return menuDatas;
	}
	
}

