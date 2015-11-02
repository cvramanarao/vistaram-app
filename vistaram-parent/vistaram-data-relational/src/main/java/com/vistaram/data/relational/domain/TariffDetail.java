package com.vistaram.data.relational.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tariff_details database table.
 * 
 */
@Entity
@Table(name="tariff_details")
@NamedQuery(name="TariffDetail.findAll", query="SELECT t FROM TariffDetail t")
public class TariffDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="tariff_details_id")
	private int tariffDetailsId;

	@Temporal(TemporalType.DATE)
	@Column(name="checkin_date")
	private Date checkinDate;

	@Temporal(TemporalType.DATE)
	@Column(name="checkout_date")
	private Date checkoutDate;

	@Column(name="no_of_nights")
	private int noOfNights;

	@Column(name="rate_per_room")
	private double ratePerRoom;

	@Column(name="total_rate")
	private double totalRate;

	//bi-directional many-to-one association to BookingDetail
	@ManyToOne
	@JoinColumn(name="booking_details_booking_id")
	private BookingDetail bookingDetail;

	public TariffDetail() {
	}

	public int getTariffDetailsId() {
		return this.tariffDetailsId;
	}

	public void setTariffDetailsId(int tariffDetailsId) {
		this.tariffDetailsId = tariffDetailsId;
	}

	public Date getCheckinDate() {
		return this.checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return this.checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getNoOfNights() {
		return this.noOfNights;
	}

	public void setNoOfNights(int noOfNights) {
		this.noOfNights = noOfNights;
	}

	public double getRatePerRoom() {
		return this.ratePerRoom;
	}

	public void setRatePerRoom(double ratePerRoom) {
		this.ratePerRoom = ratePerRoom;
	}

	public double getTotalRate() {
		return this.totalRate;
	}

	public void setTotalRate(double totalRate) {
		this.totalRate = totalRate;
	}

	public BookingDetail getBookingDetail() {
		return this.bookingDetail;
	}

	public void setBookingDetail(BookingDetail bookingDetail) {
		this.bookingDetail = bookingDetail;
	}

}