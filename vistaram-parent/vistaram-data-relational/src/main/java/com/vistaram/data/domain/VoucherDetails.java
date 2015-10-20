package com.vistaram.data.domain;

import java.text.DateFormat;
import java.util.Date;

public class VoucherDetails {
	
	private String bookingAgent;
	private String voucherNumber;
	private String guestName;
	private Date bookingDate;
	private Date checkInDate;
	private Date checkOutDate;
	
	
	
	
	
	
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
	
	@Override
	public String toString() {
		return "VoucherDetails [bookingAgent=" + bookingAgent
				+ ", voucherNumber=" + voucherNumber + ", guestName="
				+ guestName + ", bookingDate=" + bookingDate + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate + "]";
	}
	
	
	

}
