package com.yzsl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yzsl.bean.RightData;



public class TreeConverter {
	
	public static List<Map<String, Object>> Convert2Children(List<RightData> ts) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (ts.size() < 1)
			return list;
		
		for(RightData rightData : ts){
			if(rightData.getFFunID()==null||StringUtils.equals("", rightData.getFFunID())){
				Map<String, Object> child = new HashMap<String, Object>();
				child.put("funID", rightData.getFunID());
				child.put("ffunID", rightData.getFFunID());
				child.put("funName", rightData.getFunName());
				child.put("checked", rightData.getChecked());
				child.put("expanded", rightData.getExpanded());
				child.put("note", rightData.getNote());
				System.setProperty(rightData.getFunID(), "true");
				child.put("children", getChildren(ts, rightData));
				child.put("leaf", Boolean.getBoolean(rightData.getFunID()));
				list.add(child);
			}
		}
		return list;
	}
	
	public static List<Map<String, Object>> getChildren(List<RightData> ts, RightData p) {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (RightData rightData : ts) {
			if(rightData.getFFunID()!=null&&!StringUtils.equals("", rightData.getFFunID())){
				if(StringUtils.equals(p.getFunID(), rightData.getFFunID())){//列表中有父类id
					System.setProperty(p.getFunID(), "false");
					Map<String, Object> child = new HashMap<String, Object>();
					child.put("checked", rightData.getChecked());
					child.put("expanded", rightData.getExpanded());
					child.put("funID", rightData.getFunID());
					child.put("funName", rightData.getFunName());
					child.put("ffunID", rightData.getFFunID());
					child.put("note", rightData.getNote());
					System.setProperty(rightData.getFunID(), "true");
					child.put("children", getChildren(ts, rightData));
					child.put("leaf", Boolean.getBoolean(rightData.getFunID()));
					list.add(child);
				}
			}
		}
		return list;
	}
}
