package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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
	@Column(name="hotel_id")
	private int hotelId;

	private String address;

	private String city;

	private String country;

	@Column(name="hotel_identifier_name")
	private String hotelIdentifierName;

	@Column(name="hotel_name")
	private String hotelName;

	private String pincode;

	//bi-directional many-to-one association to BookingDetail
	@OneToMany(mappedBy="hotelDetail")
	private List<BookingDetail> bookingDetails;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="users_username")
	private User user;

	public HotelDetail() {
	}

	public int getHotelId() {
		return this.hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHotelIdentifierName() {
		return this.hotelIdentifierName;
	}

	public void setHotelIdentifierName(String hotelIdentifierName) {
		this.hotelIdentifierName = hotelIdentifierName;
	}

	public String getHotelName() {
		return this.hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getPincode() {
		return this.pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public List<BookingDetail> getBookingDetails() {
		return this.bookingDetails;
	}

	public void setBookingDetails(List<BookingDetail> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

	public BookingDetail addBookingDetail(BookingDetail bookingDetail) {
		getBookingDetails().add(bookingDetail);
		bookingDetail.setHotelDetail(this);

		return bookingDetail;
	}

	public BookingDetail removeBookingDetail(BookingDetail bookingDetail) {
		getBookingDetails().remove(bookingDetail);
		bookingDetail.setHotelDetail(null);

		return bookingDetail;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}