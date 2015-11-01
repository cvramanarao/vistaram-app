package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the inclusions database table.
 * 
 */
//@Entity
//@Table(name="inclusions")
//@NamedQuery(name="Inclusion.findAll", query="SELECT i FROM Inclusion i")
public class Inclusion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="property_name", nullable=false, length=50)
	private String propertyName;

	public Inclusion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}