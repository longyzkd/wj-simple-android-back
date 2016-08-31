package com.yzsl.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "YZSQ_LOOKUP_ZZ")
public class LookUpZZ { //闸站数据字典

	/**
	 * 闸站code
	 */
	@Id
	@Column(name = "STCD")
	private String stcd;
	
	/**
	 * 上水位
	 */
	@Column(name = "NAME")
	private String name;


	public String getStcd() {
		return stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
