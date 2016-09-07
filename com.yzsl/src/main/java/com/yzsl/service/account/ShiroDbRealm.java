/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.service.account;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzsl.bean.User;
import com.yzsl.util.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	
	
	@Autowired
	protected AccountService accountService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("doGetAuthenticationInfo----");
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = null;
		try {
			user = accountService.findUserByLoginName(token.getUsername());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user != null) {
			
			byte[] salt = Encodes.decodeHex(user.getSalt());//16进制的
			return new SimpleAuthenticationInfo(user,
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
			
//			return new SimpleAuthenticationInfo();
		} else {
			return null;
		}
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
		
	}
	
	

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
//		User user = accountService.findUserByLoginName(shiroUser.loginName);
//		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addRoles(user.getRoleList());
//		return info;
		return null;
	}


	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
