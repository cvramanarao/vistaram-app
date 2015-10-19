package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the guest_details database table.
 * 
 */
@Entity
@Table(name="guest_details")
@NamedQuery(name="GuestDetail.findAll", query="SELECT g FROM GuestDetail g")
public class GuestDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=250)
	private String address;

	@Column(nullable=false)
	private int contact_Num;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dob;

	@Column(nullable=false, length=200)
	private String email_Id;

	@Column(nullable=false, length=30)
	private String first_Name;

	@Column(nullable=false, length=1)
	private String gender;

	@Column(nullable=false, length=40)
	private String last_Name;

	@Column(nullable=false, length=30)
	private String occupation;

	@Column(nullable=false)
	private int zip_Code;

	public GuestDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getContact_Num() {
		return this.contact_Num;
	}

	public void setContact_Num(int contact_Num) {
		this.contact_Num = contact_Num;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail_Id() {
		return this.email_Id;
	}

	public void setEmail_Id(String email_Id) {
		this.email_Id = email_Id;
	}

	public String getFirst_Name() {
		return this.first_Name;
	}

	public void setFirst_Name(String first_Name) {
		this.first_Name = first_Name;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLast_Name() {
		return this.last_Name;
	}

	public void setLast_Name(String last_Name) {
		this.last_Name = last_Name;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getZip_Code() {
		return this.zip_Code;
	}

	public void setZip_Code(int zip_Code) {
		this.zip_Code = zip_Code;
	}

}