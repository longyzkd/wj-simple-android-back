package com.yzsl.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "v_data")
public class Data { //视图数据

	/**
	 * 闸站code
	 */
	@Id
	@Column(name = "ZBM")
	private String zbmCode;
	/**
	 * 闸站名
	 */
	@Transient
	private String zbmName;  
	/**
	 * 上水位
	 */
	@Column(name = "SSW")
	private Double ssw;
	/**
	 * 下水位
	 */
	@Column(name = "XSW")
	private Double xsw;
	/**
	 * 流量
	 */
	@Column(name = "LN")
	private Double ln;
	/**
	 * 时间
	 */
	@Column(name = "TM")
	private Date tm;
	
	
	public String getZbmCode() {
		return zbmCode;
	}
	public void setZbmCode(String zbmCode) {
		this.zbmCode = zbmCode;
	}
	public String getZbmName() {
		return zbmName;
	}
	public void setZbmName(String zbmName) {
		this.zbmName = zbmName;
	}
	public Double getSsw() {
		return ssw;
	}
	public void setSsw(Double ssw) {
		this.ssw = ssw;
	}
	public Double getXsw() {
		return xsw;
	}
	public void setXsw(Double xsw) {
		this.xsw = xsw;
	}
	public Double getLn() {
		return ln;
	}
	public void setLn(Double ln) {
		this.ln = ln;
	}
	public Date getTm() {
		return tm;
	}
	public void setTm(Date tm) {
		this.tm = tm;
	}
	
	
}
