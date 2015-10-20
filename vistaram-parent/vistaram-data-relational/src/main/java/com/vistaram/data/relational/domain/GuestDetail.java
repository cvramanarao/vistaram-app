package com.vistaram.data.relational.domain;

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

	@Column(name="contact_num", nullable=false)
	private int contactNum;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date dob;

	@Column(name="email_id", nullable=false, length=200)
	private String emailId;

	@Column(name="first_name", nullable=false, length=30)
	private String firstName;

	@Column(nullable=false, length=1)
	private String gender;

	@Column(name="last_name", nullable=false, length=40)
	private String lastName;

	@Column(nullable=false, length=30)
	private String occupation;

	@Column(name="zip_code", nullable=false)
	private int zipCode;

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

	public int getContactNum() {
		return this.contactNum;
	}

	public void setContactNum(int contactNum) {
		this.contactNum = contactNum;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

}