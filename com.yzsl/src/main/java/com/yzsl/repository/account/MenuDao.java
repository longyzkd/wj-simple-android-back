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
import org.springframework.util.CollectionUtils;

import com.yzsl.bean.Menu;
import com.yzsl.bean.User;
import com.yzsl.repository.CommonDao;

@Component
public class MenuDao extends CommonDao<Menu>  {
	
	public List<Menu> find(){
		
		DetachedCriteria  detachedCriteria =  DetachedCriteria.forClass(Menu.class);
		List<Menu> result = find(detachedCriteria);
		
		return result;
		
		
	}

	
}
