/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yzsl.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzsl.bean.Data;
import com.yzsl.repository.DataDao;
import com.yzsl.util.DBContextHolder;
import com.yzsl.util.DateUtil;

/**
 * 用户管理类.
 * 
 * @author wj
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class DataService {


	private static Logger logger = LoggerFactory.getLogger(DataService.class);
	
	@Autowired
	private DataDao dao;

	/**
	 * 获得水情最新数据
	 * @return
	 */
	public List<Data> getSqData() {
		return dao.findSq();
	}

	public Map<String,Object> getYzzData() {
		String sql = "select * from water where datetime= (select max(datetime) from water)";
		List<Map> tmp = dao.findBySql(sql);
		Assert.isTrue(tmp.size()==1);
		Map baseInfo = tmp.get(0);
		
		String sqlZm = "select * from zm where datetime= (select max(datetime) from zm)";
		List<Map> zmList = dao.findBySql(sqlZm);
		Assert.isTrue(zmList.size()==1);
		Map zmkd = zmList.get(0);
		
		String sqlZmdl = "select * from dl where datetime= (select max(datetime) from dl)";
		List<Map> zmdlList = dao.findBySql(sqlZmdl);
		Assert.isTrue(zmdlList.size()==1);
		Map zmdl = zmdlList.get(0);
		
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("baseInfo", baseInfo); //基本信息
		result.put("zmkd", zmkd);//闸门开度
		result.put("zmdl", zmdl);//闸门电流
		
		return result;
	}
	
	public void syncData() {
		
		
		dao.syncData();
		
	}


	/**
	 * @author wj
	 * @date 2016-8-19
	 * 切换不同的数据源，同步远程最新数据到本地
	 * @param tableName 表名
	 * @param stcd 字段
	 * @param dbType 数据库类型
	 */
	private void syncOne(String tableName,String stcd,String dbType){
		
		
		if("oracle".equals(dbType)){ //如果远程数据库是ORACLE
			 DBContextHolder.setDBType(DBContextHolder.dataSourceTo);  
			
			String hql = "select MAX( TM ) from "+ tableName +" WHERE STCD = '"+stcd+"'";
			List r = dao.find(hql);
			Date latestTime = (Date)r.get(0);
			
			
			
		   DBContextHolder.setDBType(DBContextHolder.dataSourceOracle);  //切换到远程ORACLE数据库，查询远程最新数据
		   
		   String remoteTableName = tableName.replace("YZSQ", "ST");
		   String gtTimeResultSql = "select * from "+remoteTableName+" where STCD= '"+ stcd +"' and tm> :tm"  ;
		   Map<String,Object> params = Maps.newHashMap();
		   params.put("tm", latestTime);
		  List<Map> gtTimeResult = dao.findBySql(gtTimeResultSql, params);
		   
		  DBContextHolder.setDBType(DBContextHolder.dataSourceTo);   //切换到本地目标数据库，插入数据到本地
		   for(Map map: gtTimeResult){
			 StringBuilder inserSql = 	new StringBuilder(" insert into "+tableName+" values (");
			 int i=0;
			   for (Object key : map.keySet()) {
				   i++;
				   System.out.println("key= "+ String.valueOf(key) + " and value= " + map.get(key));
				   Object value = map.get(key);
				   if(i< map.keySet().size()){
					   if(value instanceof String){
						   inserSql.append("'"+(String)value+"',");
					   }else if(value instanceof Date){
						   String pattern = "yyyy-MM-dd HH24:mi:ss";
						   Date converted = (Date)value;
						   inserSql.append("to_date('"+DateUtil.dateToString(converted, pattern)  +"', '"+pattern+"'),");
					   }else if(value instanceof Integer){
						   inserSql.append((Integer)value+",");
					   }else {
						   inserSql.append(value+",");
					   }
				   }else{
					   
					   if(value instanceof String){
						   inserSql.append("'"+(String)value+"')");
					   }else if(value instanceof Date){
						   String pattern = "yyyy-MM-dd HH24:mi:ss";
						   Date converted = (Date)value;
						   inserSql.append("to_date('"+DateUtil.dateToString(converted, pattern)  +"', '"+pattern+"') )");
					   }else if(value instanceof Integer){
						   inserSql.append((Integer)value+")");
					   }else {
						   inserSql.append(value+")");
					   }
				   }
				   
				   dao.executeSql(inserSql.toString());
				   
				  }
		   }
		   
			//String inserSql = 	" insert into "+tableName+" values "+
				
			
		}else if("mysql".equals(dbType)){//如果远程数据库是mysql
			
			
			
		}
		
	}

	public List<Map> getYzzDynamicData(Date dateFrom, Date dateTo, String selectType,Integer page,Integer count) throws Exception {
		String tableName ="";
		
		if("sw".equals(selectType)){
			tableName="water";
			
		}/*else if("ll".equals(selectType)){
			tableName="mvalue";
			
		}*/else if("dl".equals(selectType)){
			tableName="dl";
		}
		else if("zmkd".equals(selectType)){
			tableName="zm";
		}
		else if("czjl".equals(selectType)){
			tableName="operation";
		}
		else if("bjjl".equals(selectType)){
			tableName="alarm";
		}
		
		if(StringUtils.isEmpty(tableName)){
			
			throw new Exception("非法类型："+selectType+",没有对应的表");
		}
		
		String sql = "select * from "+tableName+" where datetime >:dateFrom and datetime<:dateTo ";
		
		logger.debug("sql----------"+sql);
		Map<String,Object> params = Maps.newHashMap();
		params.put("dateFrom", dateFrom);
		params.put("dateTo", dateTo);
		if(page ==null || count==null){
			return dao.findBySql(sql, params);
		}
		return dao.findBySql(sql, params, page, count);
	}

	public Map<String, Object> getYzzDlData() {
		String sqlZmdl = "select * from dl where datetime= (select max(datetime) from dl)";
		List<Map> zmdlList = dao.findBySql(sqlZmdl);
		Assert.isTrue(zmdlList.size()==1);
		Map zmdl = zmdlList.get(0);
		

		Map<String,Object> result = Maps.newHashMap();
		result.put("zmdl", zmdl);//闸门电流
		
		return result;
	}

	public Map<String, Object> getYzzSwData() {
		String sql = "select * from water where datetime= (select max(datetime) from water)";
		List<Map> tmp = dao.findBySql(sql);
		Assert.isTrue(tmp.size()==1);
		Map baseInfo = tmp.get(0);
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("baseInfo", baseInfo); //基本信息
		
		return result;
	}

	public Map<String, Object> getYzzKdData() {
		String sqlZm = "select * from zm where datetime= (select max(datetime) from zm)";
		List<Map> zmList = dao.findBySql(sqlZm);
		Assert.isTrue(zmList.size()==1);
		Map zmkd = zmList.get(0);
		
		Map<String,Object> result = Maps.newHashMap();
		result.put("zmkd", zmkd);//闸门开度
		
		return result;
	}


	


}
