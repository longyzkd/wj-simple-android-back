/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.service.account;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.yzsl.bean.Menu;
import com.yzsl.bean.Tmanageruserfun;
import com.yzsl.bean.TmanageruserfunId;
import com.yzsl.bean.User;
import com.yzsl.repository.account.MenuDao;
import com.yzsl.repository.account.UserDao;
import com.yzsl.util.BeanUtils;
import com.yzsl.util.Digests;
import com.yzsl.util.Encodes;
import com.yzsl.util.Json;
import com.yzsl.util.Page;

/**
 * 用户管理类.
 * 
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private MenuDao menuDao;

	
	@Transactional(readOnly=true)
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}


	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return user.getUsername();
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		
			
			byte[] salt = Digests.generateSalt(SALT_SIZE);
			user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}


	/**
	 * 保存
	 * @param user
	 */
	public void create(User user) {
		entryptPassword(user);
		userDao.save(user);
		
	}
	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.update(user);
		
	}

	public void del(User u) {
			userDao.delete(u.getUserId());
		
	}
	public void del(List<User> beanList) {
			if(!CollectionUtils.isEmpty(beanList)){
				for(User user:beanList){
					del(user);
				}
			}
			
		
	}

	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.update(user);
		
	}


	public static void main(String[] a){
		
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		//logger.info("salt"+Encodes.encodeHex(salt));
		
		byte[] hashPassword = Digests.sha1("321".getBytes(),  Encodes.decodeHex("848c7f1e3c58ca09"), HASH_INTERATIONS);
		
//		String  hashPassword = new SimpleHash("SHA-1", "321", ByteSource.Util.bytes( Encodes.decodeHex("848c7f1e3c58ca09")), HASH_INTERATIONS).toString();
		logger.info("pwd   "+ Digests.encryptPassword("123", "8449a736b311440a") );
//		logger.info("pwd"+hashPassword);
	}


	public List<Menu> findAllMenus() {
		return menuDao.find();
	}


	public Page<User> getUsers(Page<User> page, User user) {
		Page<User> p = userDao.findUsers(page,user);
		return p;
	}


	public List<Tmanageruserfun> findMenusBy(String userId) {
		String hql = "from Tmanageruserfun where id.userId=:userId";
		Map<String, Object> paramter = Maps.newHashMap();
		paramter.put("userId", userId);
		return menuDao.findme(hql, paramter);
	}
	
	
	public void saveUser(User data,String[] funidvalue) throws Exception{
		if (data != null) {
			User u = userDao.findByUserid(data.getUserId());
			
			if (u != null) {
				throw new Exception("新建用户失败，用户名已存在！");
			} else {
				entryptPassword(data);
//				data.setPassword(MD5Util.md5("123456"));
				userDao.save(data);
				if(funidvalue != null)
				{
					String[] arrFunidStrings = funidvalue;
					for (int i = 0; i < arrFunidStrings.length; i++) {
						TmanageruserfunId manageruserfunId = new TmanageruserfunId();
						Tmanageruserfun manageruserfun = new Tmanageruserfun();
						if(!"".equals(arrFunidStrings[i]))
						{
							manageruserfunId.setFunId(arrFunidStrings[i]);
							manageruserfunId.setUserId(data.getUserId());
							manageruserfun.setId(manageruserfunId);
							userDao.saveme(manageruserfun);
						}
						
					}
				}
				
			}
			
		}
	}
	
	public void  updateUser(User data, String[] funidvalue){
		User t = userDao.findByUserid(data.getUserId());
		BeanUtils.copyNotNullProperties(data, t,new String[]{"password"});//密码不更新，密码单独更新
		
		update(t);
		
		if(funidvalue != null)
		{
			userDao.deleteUserMenu(data.getUserId());
			
			String[] arrFunidStrings = funidvalue;
			for (int i = 0; i < arrFunidStrings.length; i++) {
				TmanageruserfunId manageruserfunId = new TmanageruserfunId();
				Tmanageruserfun manageruserfun = new Tmanageruserfun();
				if(!"".equals(arrFunidStrings[i]))
				{
					manageruserfunId.setFunId(arrFunidStrings[i]);
					manageruserfunId.setUserId(data.getUserId());
					manageruserfun.setId(manageruserfunId);
					userDao.saveme(manageruserfun);
				}
				
			}
		}
	}


	public User one(User user) {
		
		return userDao.findByUserid(user.getUserId());
	}


	public List<Menu> findMenuByuserId(String userIdString) {
		List<Menu>  menus =	menuDao.findMenusBy(userIdString);
		return menus;
	}
	
}
