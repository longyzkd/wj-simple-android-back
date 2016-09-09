/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.repository.account;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.yzsl.bean.Menu;
import com.yzsl.repository.CommonDao;

@Component
public class MenuDao extends CommonDao<Menu>  {
	
	public List<Menu> find(){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Menu.class);
		List<Menu> result = find(detachedCriteria);
		
		return result;
		
		
	}

	public List<Menu> findMenusBy(String userIdString) {
		String sql = "SELECT t.* FROM tmanagermenu t JOIN tmanageruserfun  a ON a.FunID=t.FunID WHERE a.UserID=:p1";
		Map<String,Object> params = Maps.newHashMap(); 
		params.put("p1", userIdString);
		return findBySql(sql, params, Menu.class);
	}

	
}
