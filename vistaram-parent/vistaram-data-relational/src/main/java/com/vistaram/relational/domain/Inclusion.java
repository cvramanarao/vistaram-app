package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Inclusions database table.
 * 
 */
@Entity
@Table(name="Inclusions")
@NamedQuery(name="Inclusion.findAll", query="SELECT i FROM Inclusion i")
public class Inclusion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=50)
	private String property_Name;

	public Inclusion() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProperty_Name() {
		return this.property_Name;
	}

	public void setProperty_Name(String property_Name) {
		this.property_Name = property_Name;
	}

}