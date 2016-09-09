package com.yzsl.bean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Tmanageruserfun entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tmanageruserfun")
public class Tmanageruserfun implements java.io.Serializable {

	// Fields

	private TmanageruserfunId id;

	// Constructors

	/** default constructor */
	public Tmanageruserfun() {
	}

	/** full constructor */
	public Tmanageruserfun(TmanageruserfunId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "userId", column = @Column(name = "UserID", nullable = false, length = 100)),
			@AttributeOverride(name = "funId", column = @Column(name = "FunID", nullable = false, length = 100)) })
	public TmanageruserfunId getId() {
		return this.id;
	}

	public void setId(TmanageruserfunId id) {
		this.id = id;
	}

}