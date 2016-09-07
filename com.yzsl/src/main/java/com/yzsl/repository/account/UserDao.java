/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.repository.account;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.yzsl.bean.User;
import com.yzsl.repository.CommonDao;

@Component
public class UserDao extends CommonDao<User>  {
	
	public User findByLoginName(String loginName){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("userId", loginName));
		List<User> result = find(detachedCriteria);
		
		return CollectionUtils.isEmpty(result)?null:result.get(0);
		
		
		
	}
	public User findByUserid(String userid){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("userId", userid));
		List<User> result = find(detachedCriteria);
		
		return CollectionUtils.isEmpty(result)?null:result.get(0);
		
		
	}


	public void updatePwd(User user) {
//		ShiroUser u = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
//		Parameter p = new Parameter(user.getPassword(),DateUtils.parseDate(DateUtils.getDate()),u.loginName,user.getId());
//		update("update User set password = :p1 ,updateDate=:p2 ,updateBy=:p3  where id = :p4", p);
		
	}

	
}
