package com.yzsl.bean;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TmanageruserfunId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class TmanageruserfunId implements java.io.Serializable {

	// Fields

	private String userId;
	private String funId;

	// Constructors

	/** default constructor */
	public TmanageruserfunId() {
	}

	/** full constructor */
	public TmanageruserfunId(String userId, String funId) {
		this.userId = userId;
		this.funId = funId;
	}

	// Property accessors

	@Column(name = "UserID", nullable = false, length = 100)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "FunID", nullable = false, length = 100)
	public String getFunId() {
		return this.funId;
	}

	public void setFunId(String funId) {
		this.funId = funId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TmanageruserfunId))
			return false;
		TmanageruserfunId castOther = (TmanageruserfunId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getFunId() == castOther.getFunId()) || (this
						.getFunId() != null && castOther.getFunId() != null && this
						.getFunId().equals(castOther.getFunId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getFunId() == null ? 0 : this.getFunId().hashCode());
		return result;
	}

}