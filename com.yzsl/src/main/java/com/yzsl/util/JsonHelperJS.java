package com.yzsl.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.asm.Type;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by lxh on 2016-03-16.
 */
public class JsonHelperJS {
	
    public static String FormExtJsModel(String className, String alias,String HMD,String pre) throws ClassNotFoundException
    {
    	
    	Class t = Class.forName(className);
        StringBuilder sb = new StringBuilder();
        sb.append("Ext.define('" + alias + "', {");
        sb.append("extend: 'Ext.data.Model',");
        //sb.append("idProperty: 'Id',");
        sb.append("fields: [");

        Field[] fields=t.getDeclaredFields();  
        String[] columnName = HMD == "" ? new String[] { } : HMD.split(",");
        
        boolean bAddComma = false;
        for(int i=0;i<fields.length;i++){  
        	boolean hmd = false;//是否在黑名单中
        	
        	if (bAddComma)
                sb.append(",");
        	sb.append("{ name: '");
            sb.append(pre+fields[i].getName());
            sb.append("', mapping: '");
            sb.append(fields[i].getName());
            sb.append("', type: ");
            for (String name : columnName)
            {
                if (fields[i].getName() == name)
                    hmd = true;
            }

            if (hmd)
                sb.append("'string'");
            else
            {
	            switch (fields[i].getType().toString())
	            {
	            	case "class java.lang.Short":
	                    sb.append("'number'");
	                    break;
                    
	                case "class java.lang.Integer":
	                    sb.append("'number'");
	                    break;
	
	                case "class java.lang.Long":
	                    sb.append("'number'");
	                    break;
	
	                case "class java.lang.Float":
	                    sb.append("'number'");
	                    break;
	
	                case "class java.lang.Double":
	                    sb.append("'number'");
	                    break;
	
	                case "class java.util.Date":
	                    sb.append("'date', convert: formatJsonTime");
	                    break;

	                case "class java.lang.Boolean":
	                    sb.append("'boolean'");
	                    break;
	
	                default:
	                    sb.append("'string'");
	                    break;
	            }
            }
            sb.append(" }");
            bAddComma = true;                 
	    }  
        sb.append("]");
        sb.append("});");       
        return sb.toString();
    }
    
    
    public static String getJsonByFilter(Object object, String[] includesProperties, String[] excludesProperties) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
			String json;
			json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
