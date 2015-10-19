package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Hotel_Details database table.
 * 
 */
@Entity
@Table(name="Hotel_Details")
@NamedQuery(name="HotelDetail.findAll", query="SELECT h FROM HotelDetail h")
public class HotelDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int contact_Info;

	@Column(nullable=false, length=55)
	private String country;

	@Column(name="hotel_name", nullable=false, length=120)
	private String hotelName;

	@Column(nullable=false, length=150)
	private String mail_ID;

	@Column(nullable=false, length=255)
	private String map;

	@Column(nullable=false, length=80)
	private String place;

	@Column(nullable=false)
	private int zip_Code;

	public HotelDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getContact_Info() {
		return this.contact_Info;
	}

	public void setContact_Info(int contact_Info) {
		this.contact_Info = contact_Info;
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

	public String getMail_ID() {
		return this.mail_ID;
	}

	public void setMail_ID(String mail_ID) {
		this.mail_ID = mail_ID;
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

	public int getZip_Code() {
		return this.zip_Code;
	}

	public void setZip_Code(int zip_Code) {
		this.zip_Code = zip_Code;
	}

}