package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the hotel_details database table.
 * 
 */
@Entity
@Table(name="hotel_details")
@NamedQuery(name="HotelDetail.findAll", query="SELECT h FROM HotelDetail h")
public class HotelDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="contact_info", nullable=false)
	private int contactInfo;

	@Column(nullable=false, length=55)
	private String country;

	@Column(name="hotel_name", nullable=false, length=120)
	private String hotelName;

	@Column(nullable=false, length=150)
	private String mail_id;

	@Column(nullable=false, length=255)
	private String map;

	@Column(nullable=false, length=80)
	private String place;

	@Column(name="zip_code", nullable=false)
	private int zipCode;

	public HotelDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(int contactInfo) {
		this.contactInfo = contactInfo;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getMail_id() {
		return this.mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getMap() {
		return this.map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

}