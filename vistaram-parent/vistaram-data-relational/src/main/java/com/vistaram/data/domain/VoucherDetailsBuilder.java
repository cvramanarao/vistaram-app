package com.vistaram.data.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VoucherDetailsBuilder {
	
	public static VoucherDetails build(Map<String, String> detailsMap){
		VoucherDetails voucherDetails = new VoucherDetails();
		/*
		 * 
		 * {name of hotel & city=Vistaram Rtc Complex, Visakhapatnam, India, type of room=Royal King A/c, number of rooms=1, number of nights=1, Rate plan name=MAP, booking date=Oct. 10, 2015, 9:40 p.m., guest request=WEB SERVICES BOOKING, room 1=Adult 2 Child 0, Special Request=None, checkout date=Oct. 12, 2015, 
		 * name of guest=Santosh Pradhan, checkin date=Oct. 11, 2015, inclusions=Accommodation , Breakfast and Dinner(MAP), Booking Type=mobile}
		 */
		
		voucherDetails.setVoucherNumber(detailsMap.get("voucherNumber"));
		voucherDetails.setGuestName(detailsMap.get("name of guest"));
		String bookingDateStr = detailsMap.get("booking date");
		bookingDateStr = bookingDateStr.replace(".", "");
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date bookingDate = null;
		try {
			sdf.applyLocalizedPattern("MMM dd, yyyy, h:mm a");
			bookingDate = sdf.parse(bookingDateStr); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetails.setBookingDate(bookingDate);
		
		String checkInDateStr = detailsMap.get("checkin date");
		Date checkInDate = null;
		try {
		    sdf.applyLocalizedPattern("MMM. dd, yyyy");
		    checkInDate = sdf.parse(checkInDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetails.setCheckInDate(checkInDate);
		
		
		String checkOutDateStr = detailsMap.get("checkout date");

		Date checkOutDate = null;
		try {
			checkOutDate = sdf.parse(checkOutDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetails.setCheckOutDate(checkOutDate);
		
		
		
		detailsMap.get("name of hotel & city");
		detailsMap.get("type of room");
		detailsMap.get("number of rooms");
		detailsMap.get("number of nights");
		detailsMap.get("Rate plan name");
		detailsMap.get("guest request");
		detailsMap.get("room 1");
		detailsMap.get("Special Request");
		detailsMap.get("inclusions");
		detailsMap.get("Booking Type");
		
		return voucherDetails;
	}

}
