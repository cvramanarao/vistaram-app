package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Booking_Details database table.
 * 
 */
@Entity
@Table(name="Booking_Details")
public class BookingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false, length=120)
	private String booking_Agent;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date booking_Date;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date checking_Date;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date checkout_Date;

	@Lob
	@Column(name="comments_or_requests", nullable=false)
	private byte[] commentsOrRequests;

	@Column(name="guest_id", nullable=false)
	private int guestId;

	@Column(name="hotel_details_id", nullable=false)
	private int hotelDetailsId;

	@Column(nullable=false)
	private int no_Of_Nights_Stayed;

	@Column(nullable=false)
	private int number_Of_Rooms;

	@Column(name="properties_id", nullable=false)
	private int propertiesId;

	@Column(name="rate_plan_id", nullable=false)
	private int ratePlanId;

	@Column(name="room_details_id", nullable=false)
	private int roomDetailsId;

	@Column(nullable=false, length=30)
	private String voucher_id;

	public BookingDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBooking_Agent() {
		return this.booking_Agent;
	}

	public void setBooking_Agent(String booking_Agent) {
		this.booking_Agent = booking_Agent;
	}

	public Date getBooking_Date() {
		return this.booking_Date;
	}

	public void setBooking_Date(Date booking_Date) {
		this.booking_Date = booking_Date;
	}

	public Date getChecking_Date() {
		return this.checking_Date;
	}

	public void setChecking_Date(Date checking_Date) {
		this.checking_Date = checking_Date;
	}

	public Date getCheckout_Date() {
		return this.checkout_Date;
	}

	public void setCheckout_Date(Date checkout_Date) {
		this.checkout_Date = checkout_Date;
	}

	public byte[] getCommentsOrRequests() {
		return this.commentsOrRequests;
	}

	public void setCommentsOrRequests(byte[] commentsOrRequests) {
		this.commentsOrRequests = commentsOrRequests;
	}

	public int getGuestId() {
		return this.guestId;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public int getHotelDetailsId() {
		return this.hotelDetailsId;
	}

	public void setHotelDetailsId(int hotelDetailsId) {
		this.hotelDetailsId = hotelDetailsId;
	}

	public int getNo_Of_Nights_Stayed() {
		return this.no_Of_Nights_Stayed;
	}

	public void setNo_Of_Nights_Stayed(int no_Of_Nights_Stayed) {
		this.no_Of_Nights_Stayed = no_Of_Nights_Stayed;
	}

	public int getNumber_Of_Rooms() {
		return this.number_Of_Rooms;
	}

	public void setNumber_Of_Rooms(int number_Of_Rooms) {
		this.number_Of_Rooms = number_Of_Rooms;
	}

	public int getPropertiesId() {
		return this.propertiesId;
	}

	public void setPropertiesId(int propertiesId) {
		this.propertiesId = propertiesId;
	}

	public int getRatePlanId() {
		return this.ratePlanId;
	}

	public void setRatePlanId(int ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public int getRoomDetailsId() {
		return this.roomDetailsId;
	}

	public void setRoomDetailsId(int roomDetailsId) {
		this.roomDetailsId = roomDetailsId;
	}

	public String getVoucher_id() {
		return this.voucher_id;
	}

	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}

}