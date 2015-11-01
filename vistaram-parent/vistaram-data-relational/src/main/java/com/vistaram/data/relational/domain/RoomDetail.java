package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-many association to BookingDetail
	@ManyToMany
	@JoinTable(
		name="room_details_has_booking_details"
		, joinColumns={
			@JoinColumn(name="room_details_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="booking_details_booking_id")
			}
		)
	private List<BookingDetail> bookingDetails;

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

	public List<BookingDetail> getBookingDetails() {
		return this.bookingDetails;
	}

	public void setBookingDetails(List<BookingDetail> bookingDetails) {
		this.bookingDetails = bookingDetails;
	}

}