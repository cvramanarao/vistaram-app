package com.vistaram.data.relational.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the booking_details database table.
 * 
 */
@Entity
@Table(name="booking_details")

@NamedQueries({
	@NamedQuery(name="BookingDetail.findAll", query="SELECT b FROM BookingDetail b"),
	@NamedQuery(name="BookingDetail.findByVoucherId", query="SELECT b FROM BookingDetail b where b.voucherId=:voucherId")
})
public class BookingDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="booking_id")
	private int bookingId;

	@Column(name="booking_agent")
	private String bookingAgent;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="booking_date")
	private Date bookingDate;

	@Temporal(TemporalType.DATE)
	@Column(name="checkin_date")
	private Date checkinDate;

	@Temporal(TemporalType.DATE)
	@Column(name="checkout_date")
	private Date checkoutDate;

	@Column(name="no_of_nights")
	private int noOfNights;

	@Column(name="no_of_rooms")
	private int noOfRooms;

	@Column(name="payment_type")
	private String paymentType;

	@Column(name="total_amount")
	private double totalAmount;

	@Column(name="total_tax")
	private double totalTax;

	@Column(name="voucher_id")
	private String voucherId;
	
	private String source;

	//bi-directional many-to-one association to GuestDetail
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="guest_details_guest_id")
	private GuestDetail guestDetail;

	//bi-directional many-to-one association to HotelDetail
	@ManyToOne
	@JoinColumn(name="hotel_details_hotel_id")
	private HotelDetail hotelDetail;

	//bi-directional many-to-one association to RoomDetail
	@OneToMany(mappedBy="bookingDetail", cascade=CascadeType.ALL)
	private List<RoomDetail> roomDetails;

	//bi-directional many-to-one association to TariffDetail
	@OneToMany(mappedBy="bookingDetail", cascade=CascadeType.ALL)
	private List<TariffDetail> tariffDetails;

	public BookingDetail() {
	}

	public int getBookingId() {
		return this.bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
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

	public int getNoOfRooms() {
		return this.noOfRooms;
	}

	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTotalTax() {
		return this.totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public String getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public GuestDetail getGuestDetail() {
		return this.guestDetail;
	}

	public void setGuestDetail(GuestDetail guestDetail) {
		this.guestDetail = guestDetail;
	}

	public HotelDetail getHotelDetail() {
		return this.hotelDetail;
	}

	public void setHotelDetail(HotelDetail hotelDetail) {
		this.hotelDetail = hotelDetail;
	}

	public List<RoomDetail> getRoomDetails() {
		return this.roomDetails;
	}

	public void setRoomDetails(List<RoomDetail> roomDetails) {
		this.roomDetails = roomDetails;
	}

	public RoomDetail addRoomDetail(RoomDetail roomDetail) {
		getRoomDetails().add(roomDetail);
		roomDetail.setBookingDetail(this);

		return roomDetail;
	}

	public RoomDetail removeRoomDetail(RoomDetail roomDetail) {
		getRoomDetails().remove(roomDetail);
		roomDetail.setBookingDetail(null);

		return roomDetail;
	}

	public List<TariffDetail> getTariffDetails() {
		return this.tariffDetails;
	}

	public void setTariffDetails(List<TariffDetail> tariffDetails) {
		this.tariffDetails = tariffDetails;
	}

	public TariffDetail addTariffDetail(TariffDetail tariffDetail) {
		getTariffDetails().add(tariffDetail);
		tariffDetail.setBookingDetail(this);

		return tariffDetail;
	}

	public TariffDetail removeTariffDetail(TariffDetail tariffDetail) {
		getTariffDetails().remove(tariffDetail);
		tariffDetail.setBookingDetail(null);

		return tariffDetail;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	


}