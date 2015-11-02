package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the room_details database table.
 * 
 */
@Entity
@Table(name="room_details")
@NamedQuery(name="RoomDetail.findAll", query="SELECT r FROM RoomDetail r")
public class RoomDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="no_of_adults")
	private int noOfAdults;

	@Column(name="no_of_children")
	private int noOfChildren;

	@Column(name="room_name")
	private String roomName;

	@Column(name="room_rate")
	private double roomRate;

	@Column(name="room_type")
	private String roomType;

	//bi-directional many-to-one association to BookingDetail
	@ManyToOne
	@JoinColumn(name="booking_details_booking_id")
	private BookingDetail bookingDetail;

	public RoomDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoOfAdults() {
		return this.noOfAdults;
	}

	public void setNoOfAdults(int noOfAdults) {
		this.noOfAdults = noOfAdults;
	}

	public int getNoOfChildren() {
		return this.noOfChildren;
	}

	public void setNoOfChildren(int noOfChildren) {
		this.noOfChildren = noOfChildren;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public double getRoomRate() {
		return this.roomRate;
	}

	public void setRoomRate(double roomRate) {
		this.roomRate = roomRate;
	}

	public String getRoomType() {
		return this.roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BookingDetail getBookingDetail() {
		return this.bookingDetail;
	}

	public void setBookingDetail(BookingDetail bookingDetail) {
		this.bookingDetail = bookingDetail;
	}

}