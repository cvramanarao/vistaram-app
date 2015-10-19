package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Rate_Plan database table.
 * 
 */
@Entity
@Table(name="Rate_Plan")
@NamedQuery(name="RatePlan.findAll", query="SELECT r FROM RatePlan r")
public class RatePlan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=50)
	private String rate_Plan_Name;

	public RatePlan() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRate_Plan_Name() {
		return this.rate_Plan_Name;
	}

	public void setRate_Plan_Name(String rate_Plan_Name) {
		this.rate_Plan_Name = rate_Plan_Name;
	}

}