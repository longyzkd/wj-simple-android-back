package com.yzsl.util;

public class DBContextHolder{  
    public static final String dataSourceTo = "dataSourceTo";  
    public static final String dataSourceMySql = "dataSourceMySql";  
    public static final String dataSourceOracle = "dataSourceOracle";  
    public static final String dataSourceSqlserver = "dataSourceSqlserver";  
      
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
      
    public static void setDBType(String dbType) {  
        contextHolder.set(dbType);  
    }  
      
    public static String getDBType() {  
        return contextHolder.get();  
    }  
      
    public static void clearDBType() {  
        contextHolder.remove();  
    }  
}  