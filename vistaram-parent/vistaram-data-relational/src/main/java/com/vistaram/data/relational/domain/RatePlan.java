package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rate_plan database table.
 * 
 */
//@Entity
//@Table(name="rate_plan")
//@NamedQuery(name="RatePlan.findAll", query="SELECT r FROM RatePlan r")
public class RatePlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="rate_plan_name", nullable=false, length=50)
	private String ratePlanName;

	public RatePlan() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRatePlanName() {
		return this.ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

}