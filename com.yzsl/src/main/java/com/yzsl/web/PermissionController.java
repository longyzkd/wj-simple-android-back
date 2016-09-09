package com.yzsl.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.yzsl.bean.Menu;
import com.yzsl.bean.RightData;
import com.yzsl.bean.Tmanageruserfun;
import com.yzsl.service.account.AccountService;
import com.yzsl.util.Json;
import com.yzsl.util.TreeConverter;

/**
 * 用户
 * 
 * 
 */

@Controller
@RequestMapping(value = "/permission")
public class PermissionController extends CommonController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AccountService service;
	
	/**
	 * 静态树
	 * @param action
	 * @param userId
	 * @param response
	 */
	@RequestMapping(value="getRights")
	public void getRights(String action,String userId,HttpServletResponse response){//String node ，异步树，会传node过来，再根据此Node加载下级树
		List<RightData> rightDatas = null;
		if(StringUtils.equals("insert", action)){
			rightDatas = iniRightDatas();
		}
		if(StringUtils.equals("update", action)){
			rightDatas = iniRightDatas();
			List<Tmanageruserfun> tlegalPermissions = service.findMenusBy(userId);
			if(tlegalPermissions!=null&&tlegalPermissions.size()>0){
				getLegalRightChecked(rightDatas, tlegalPermissions);
			}
		}
		Json json = new Json();
		TreeConverter.Convert2Children(rightDatas);
		List<Map<String, Object>> children = Lists.newArrayList(TreeConverter.Convert2Children(rightDatas)); 
//		json.setObj(TreeConverter.Convert2Children(rightDatas));
		json.setChildren(children);//静态树必须这么玩
		json.setSuccess(true);
		writeJson(response,json);
	}
	
	public void getLegalRightChecked(List<RightData> rightDatas, List<Tmanageruserfun> tmanageruserfuns){
		for(RightData rightData : rightDatas){
			for(Tmanageruserfun tmanageruserfun : tmanageruserfuns){
				if(StringUtils.equals(rightData.getFunID(), tmanageruserfun.getId().getFunId())){
					setRightChecked(rightDatas, rightData);
				}
			}
		}
	}
	
	public void setRightChecked(List<RightData> rightDatas, RightData rightData){
		rightData.setChecked(true);
		boolean flag = false;
		for(RightData rightData2 : rightDatas){
			if(StringUtils.equals(rightData.getFFunID(), rightData2.getFunID())){
				rightData2.setChecked(true);
				rightData = rightData2;
				flag = true;
			}
		}
		if(flag){//?
			setRightChecked(rightDatas, rightData);
		}
	}
	@RequestMapping(value="iniRightDatas")
	public List<RightData> iniRightDatas(){
		List<RightData> rightDatas = new ArrayList<RightData>();
		List<Menu> tmanagerfuns = service.findAllMenus();
		if(tmanagerfuns!=null&&tmanagerfuns.size()>0){
			for(Menu tmanagerfun : tmanagerfuns){
				RightData rightData = new RightData();
				rightData.setChecked(false);
				rightData.setExpanded(false);
				rightData.setFunID(tmanagerfun.getFunId());
				rightData.setFunName(tmanagerfun.getMenuName());
				rightData.setFFunID(tmanagerfun.getFmenuId());
				rightDatas.add(rightData);
			}
		}	
		return rightDatas;
	}
	
}
