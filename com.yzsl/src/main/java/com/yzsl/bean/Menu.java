package com.yzsl.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Tmanagermenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tmanagermenu")
public class Menu implements java.io.Serializable {

	// Fields

	private String menuId;
	private String menuName;
	private String iconUrl;
	private String funUrl;
	private String fmenuId;
	private String funId;
	private Integer orderId;
	private String note;
	
	private List<Menu> childList;

	// Constructors

	/** default constructor */
	public Menu() {
	}

	/** full constructor */
	public Menu(String menuName, String iconUrl, String funUrl,
			String fmenuId, String funId, Integer orderId, String note) {
		this.menuName = menuName;
		this.iconUrl = iconUrl;
		this.funUrl = funUrl;
		this.fmenuId = fmenuId;
		this.funId = funId;
		this.orderId = orderId;
		this.note = note;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "MenuID", unique = true, nullable = false, length = 100)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "MenuName", length = 400)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "IconURL", length = 1000)
	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	@Column(name = "FunURL", length = 1000)
	public String getFunUrl() {
		return this.funUrl;
	}

	public void setFunUrl(String funUrl) {
		this.funUrl = funUrl;
	}

	@Column(name = "FMenuID", length = 1000)
	public String getFmenuId() {
		return this.fmenuId;
	}

	public void setFmenuId(String fmenuId) {
		this.fmenuId = fmenuId;
	}

	@Column(name = "FunID", length = 100)
	public String getFunId() {
		return this.funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	@Column(name = "OrderID")
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "Note", length = 2000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Transient
	public List<Menu> getChildList() {
		return childList;
	}

	public void setChildList(List<Menu> childList) {
		this.childList = childList;
	}


	
	

}