package com.vistaram.data.domain;

import java.util.Date;

public class TariffDetails {
	
	private Date checkInDate;
	private Date checkOutDate;
	private int noOfNights;
	private String currency;
	private double ratePerRoom;
	private double roomRate;
	
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
	public int getNoOfNights() {
		return noOfNights;
	}
	public void setNoOfNights(int noOfNights) {
		this.noOfNights = noOfNights;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getRatePerRoom() {
		return ratePerRoom;
	}
	public void setRatePerRoom(double ratePerRoom) {
		this.ratePerRoom = ratePerRoom;
	}
	public double getRoomRate() {
		return roomRate;
	}
	public void setRoomRate(double roomRate) {
		this.roomRate = roomRate;
	}
	public TariffDetails(Date checkInDate, Date checkOutDate, int noOfNights,
			String currency, double ratePerRoom, double roomRate) {
		super();
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.noOfNights = noOfNights;
		this.currency = currency;
		this.ratePerRoom = ratePerRoom;
		this.roomRate = roomRate;
	}
	public TariffDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "TariffDetails [checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", noOfNights=" + noOfNights + ", currency="
				+ currency + ", ratePerRoom=" + ratePerRoom + ", roomRate="
				+ roomRate + "]";
	}
	
	
	
}
