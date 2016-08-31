package com.yzsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzsl.service.DataService;

public class MyJob {
	private static Logger logger = LoggerFactory.getLogger(MyJob.class);
	@Autowired
	private DataService service;
	
	
	private static int counter = 0;  
	
	  public void execute()  {  
//	        counter++;  
//	        
//	        logger.info("第 " + counter +" 次，同步开始--");
	        
	        try {
				service.syncData();
			} catch (Exception e) {
				e.printStackTrace();
				 logger.error("同步出现错误："+e.getMessage());;
			} 
	        
//	        logger.info("第 " + counter +" 次，同步结束--");
	    }  
}
