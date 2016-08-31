package com.yzsl.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 以后映射数据库
 */
public class User implements java.io.Serializable {

	// Fields

	private String userId;
	private String userName;
	private String password;
	private Integer userType;
	private Boolean isStoped;
	private String note;
	private String phone;

	private String  avatarPath;
	// Constructors

	/** default constructor */
	public User() {
	}
	@Column(name = "AvatarPath", length = 100)
	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	/** full constructor */
	public User(String userName, String password, Integer userType,
			Boolean isStoped, String note, String phone) {
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.isStoped = isStoped;
		this.note = note;
		this.phone = phone;
	}

	// Property accessors
	@Id
	@Column(name = "UserID", unique = true, nullable = false, length = 100)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "UserName", length = 100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "Password", length = 200)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "UserType")
	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@Column(name = "IsStoped")
	public Boolean getIsStoped() {
		return this.isStoped;
	}

	public void setIsStoped(Boolean isStoped) {
		this.isStoped = isStoped;
	}

	@Column(name = "Note", length = 2000)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "Phone", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	

	

}