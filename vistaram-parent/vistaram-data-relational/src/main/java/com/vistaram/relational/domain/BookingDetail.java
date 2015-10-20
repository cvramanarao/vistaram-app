package com.vistaram.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the booking_details database table.
 * 
 */
@Entity
@Table(name="booking_details")
@NamedQuery(name="BookingDetail.findAll", query="SELECT b FROM BookingDetail b")
public class BookingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="booking_agent", nullable=false, length=120)
	private String bookingAgent;

	@Temporal(TemporalType.DATE)
	@Column(name="booking_date", nullable=false)
	private Date bookingDate;

	@Temporal(TemporalType.DATE)
	@Column(name="checking_date", nullable=false)
	private Date checkingDate;

	@Temporal(TemporalType.DATE)
	@Column(name="checkout_date", nullable=false)
	private Date checkoutDate;

	@Lob
	@Column(name="comments_or_requests", nullable=false)
	private byte[] commentsOrRequests;

	@Column(name="guest_id", nullable=false)
	private int guestId;

	@Column(name="hotel_details_id", nullable=false)
	private int hotelDetailsId;

	@Column(name="no_of_nights_stayed", nullable=false)
	private int noOfNightsStayed;

	@Column(name="number_of_rooms", nullable=false)
	private int numberOfRooms;

	@Column(name="properties_id", nullable=false)
	private int propertiesId;

	@Column(name="rate_plan_id", nullable=false)
	private int ratePlanId;

	@Column(name="room_details_id", nullable=false)
	private int roomDetailsId;

	@Column(name="voucher_id", nullable=false, length=30)
	private String voucherId;

	public BookingDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookingAgent() {
		return this.bookingAgent;
	}

	public void setBookingAgent(String bookingAgent) {
		this.bookingAgent = bookingAgent;
	}

	public Date getBookingDate() {
		return this.bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getCheckingDate() {
		return this.checkingDate;
	}

	public void setCheckingDate(Date checkingDate) {
		this.checkingDate = checkingDate;
	}

	public Date getCheckoutDate() {
		return this.checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
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

	public int getNoOfNightsStayed() {
		return this.noOfNightsStayed;
	}

	public void setNoOfNightsStayed(int noOfNightsStayed) {
		this.noOfNightsStayed = noOfNightsStayed;
	}

	public int getNumberOfRooms() {
		return this.numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
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

	public String getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

}