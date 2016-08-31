/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.repository;


import java.util.Calendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yzsl.bean.Data;

@Component
public class DataDao extends CommonDao<Data>  {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SessionFactory sessionFactory;
	/**
	 * 获取 Session
	 */
	public Session getSession(){  
	  return sessionFactory.getCurrentSession();
	}

	public List<Data> findSq() {

//		Query query = getSession().createQuery("from Data"); //视图v_data --oracle+mysql的数据
//		query.setResultTransformer(Transformers.TO_LIST);
//		
//		List<Data> list =query.list(); 
//		
		String hql = "select d.zbmCode as zbmCode,  d.ssw as ssw,  d.xsw as xsw,d.ln as ln,d.tm as tm, look.name as zbmName "
				+ "from Data d,LookUpZZ look where d.zbmCode=look.stcd ";
		List<Data> list =find(hql,Data.class); 
		
		logger.debug("zbcode--"+list.get(0).getZbmCode());
		return list;
	
	}

	public void syncData() {
		logger.info("同步start");
		 long t1 = System.currentTimeMillis(); // 排序前取得当前时间  
		 
		SQLQuery query =  getSession().createSQLQuery("{call PROC_SYNC_LATEST_DATA()}");    
		query.executeUpdate();
		
		 long t2 = System.currentTimeMillis(); // 排序后取得当前时间  
		  
	        Calendar c = Calendar.getInstance();  
	        c.setTimeInMillis(t2 - t1);  
	  
		logger.info("同步end---"+"耗时: " + c.get(Calendar.MINUTE) + "分 "  
                + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)  
                + " 毫秒");
	}


	
}
