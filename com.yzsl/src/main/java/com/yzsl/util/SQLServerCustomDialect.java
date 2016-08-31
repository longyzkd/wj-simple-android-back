package com.yzsl.util;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.type.StandardBasicTypes;

public class SQLServerCustomDialect  extends  SQLServer2008Dialect{

	public SQLServerCustomDialect() {
		super();
		
		registerHibernateType(1, "string");     
	     registerHibernateType(-9, "string");     
	     registerHibernateType(-16, "string");     
	     registerHibernateType(3, "double");  
	       
	     registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());     
	     registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());     
	     registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());     
	     registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
	}
		
	
	
}
