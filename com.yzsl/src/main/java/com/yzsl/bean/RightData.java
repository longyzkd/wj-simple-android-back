package com.yzsl.bean;

import java.util.List;

public class RightData {
	
	private String FunID;
	private String FunName;
	private String FFunID;
	private String Note;
	private Boolean checked;
	private Boolean leaf;
	private Boolean expanded;
	private List<RightData> children;
	
	
	
	public String getFunID() {
		return FunID;
	}
	public void setFunID(String funID) {
		FunID = funID;
	}
	public String getFunName() {
		return FunName;
	}
	public void setFunName(String funName) {
		FunName = funName;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public Boolean getExpanded() {
		return expanded;
	}
	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}
	public List<RightData> getChildren() {
		return children;
	}
	public void setChildren(List<RightData> children) {
		this.children = children;
	}
	public String getFFunID() {
		return FFunID;
	}
	public void setFFunID(String fFunID) {
		FFunID = fFunID;
	}
}
