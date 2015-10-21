package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the guest_booking_properties database table.
 * 
 */
//@Entity
//@Table(name="guest_booking_properties")
//@NamedQuery(name="GuestBookingProperty.findAll", query="SELECT g FROM GuestBookingProperty g")
public class GuestBookingProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="booking_id", nullable=false)
	private int bookingId;

	@Column(name="property_id", nullable=false)
	private int propertyId;

	public GuestBookingProperty() {
	}

	public int getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getPropertyId() {
		return this.propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

}