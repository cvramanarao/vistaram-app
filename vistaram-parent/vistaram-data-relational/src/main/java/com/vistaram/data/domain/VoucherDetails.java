package com.vistaram.data.domain;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherDetails {
	
	
	/*
	 * 
	 * {name of hotel & city=Vistaram Rtc Complex, Visakhapatnam, India, 
	 * type of room=Royal King A/c, 
	 * number of rooms=1, 
	 * number of nights=1, 
	 * Rate plan name=MAP, 
	 * booking date=Oct. 10, 2015, 9:40 p.m., 
	 * guest request=WEB SERVICES BOOKING, 
	 * room 1=Adult 2 Child 0, 
	 * Special Request=None, 
	 * checkout date=Oct. 12, 2015, 
	 * name of guest=Santosh Pradhan, 
	 * checkin date=Oct. 11, 2015, 
	 * inclusions=Accommodation , Breakfast and Dinner(MAP), 
	 * Booking Type=mobile}
	 */
	
	private String bookingAgent;
	private String voucherNumber;
	private String guestName;
	private String hotelAndCity;
	private Date bookingDate;
	private Date checkInDate;
	private Date checkOutDate;
	private String roomType;
	

	private int noOfRooms;
	private int noOfNights;
	private String ratePlan;
	private String guestRequest;
	private String inclusions;
	private String bookingType;
	
	private PaymentType paymentType;
	
	private List<TariffDetails> tariffDetails;
	
	
	private Map<String, Map<String, Integer>> guestsPerRoom = new HashMap<String, Map<String, Integer>>();
	
	public String getHotelAndCity() {
		return hotelAndCity;
	}
	public void setHotelAndCity(String hotelAndCity) {
		this.hotelAndCity = hotelAndCity;
	}
	public int getNoOfRooms() {
		return noOfRooms;
	}
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public int getNoOfNights() {
		return noOfNights;
	}
	public void setNoOfNights(int noOfNights) {
		this.noOfNights = noOfNights;
	}
	public String getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}
	public String getGuestRequest() {
		return guestRequest;
	}
	public void setGuestRequest(String guestRequest) {
		this.guestRequest = guestRequest;
	}
	public String getInclusions() {
		return inclusions;
	}
	public void setInclusions(String inclusions) {
		this.inclusions = inclusions;
	}
	public String getBookingType() {
		return bookingType;
	}
	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}
	public Map<String, Map<String, Integer>> getGuestsPerRoom() {
		return guestsPerRoom;
	}
	public void setGuestsPerRoom(Map<String, Map<String, Integer>> guestsPerRoom) {
		this.guestsPerRoom = guestsPerRoom;
	}
	
	public String getBookingAgent() {
		return bookingAgent;
	}
	public void setBookingAgent(String bookingAgent) {
		this.bookingAgent = bookingAgent;
	}
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Date getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
	public List<TariffDetails> getTariffDetails() {
		return tariffDetails;
	}
	public void setTariffDetails(List<TariffDetails> tariffDetails) {
		this.tariffDetails = tariffDetails;
	}
	@Override
	public String toString() {
		return "VoucherDetails [bookingAgent=" + bookingAgent
				+ ", voucherNumber=" + voucherNumber + ", guestName="
				+ guestName + ", hotelAndCity=" + hotelAndCity
				+ ", bookingDate=" + bookingDate + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate
				+ ", roomType=" + roomType + ", noOfRooms=" + noOfRooms
				+ ", noOfNights=" + noOfNights + ", ratePlan=" + ratePlan
				+ ", guestRequest=" + guestRequest + ", inclusions="
				+ inclusions + ", bookingType=" + bookingType
				+ ", paymentType=" + paymentType + ", tariffDetails="
				+ tariffDetails + ", guestsPerRoom=" + guestsPerRoom + "]";
	}
	

	
}
