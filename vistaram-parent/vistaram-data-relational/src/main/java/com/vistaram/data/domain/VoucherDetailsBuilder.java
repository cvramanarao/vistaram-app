package com.vistaram.data.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
		bookingDateStr = correctDateString(bookingDateStr);
		System.out.println("bookingDateStr: "+bookingDateStr);
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date bookingDate = null;
		try {
			sdf.applyLocalizedPattern("MMM dd, yyyy, hh:mm a");
			bookingDate = sdf.parse(bookingDateStr); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("Error parsing booking date");
		}
		
		if(null == bookingDate) {
			
			try {
				sdf.applyLocalizedPattern("MMM dd, yyyy, hh a");
				bookingDate = sdf.parse(bookingDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.err.println("Error parsing booking date");
			}
		}
		
		voucherDetails.setBookingDate(bookingDate);
		
		String checkInDateStr = detailsMap.get("checkin date");
		checkInDateStr = correctDateString(checkInDateStr);
		System.out.println("checkInDateStr: "+checkInDateStr);
		Date checkInDate = null;
		try {
		    sdf.applyLocalizedPattern("MMM dd, yyyy");
		    checkInDate = sdf.parse(checkInDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetails.setCheckInDate(checkInDate);
		
		
		String checkOutDateStr = detailsMap.get("checkout date");
		checkOutDateStr = correctDateString(checkOutDateStr);
		System.out.println("checkOutDateStr: "+checkOutDateStr);
		Date checkOutDate = null;
		try {
			checkOutDate = sdf.parse(checkOutDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetails.setCheckOutDate(checkOutDate);
		
		
		
		voucherDetails.setHotelAndCity(detailsMap.get("name of hotel & city"));
		voucherDetails.setRoomType(detailsMap.get("type of room"));
		voucherDetails.setNoOfRooms(Integer.valueOf(detailsMap.get("number of rooms")));
		voucherDetails.setNoOfNights(Integer.valueOf(detailsMap.get("number of nights")));
		voucherDetails.setRatePlan(detailsMap.get("Rate plan name"));
		voucherDetails.setGuestRequest(detailsMap.get("guest request"));
		String room1Occupancy = detailsMap.get("room 1");
		String tokens[] = room1Occupancy.split(" ");
		Map<String, Integer> guests = new HashMap<String, Integer>();
		if(tokens[0].trim().equalsIgnoreCase("Adult")) {
			Integer noOfAdults = Integer.valueOf(tokens[1].trim());
			guests.put("Adult", noOfAdults);
		}
			
		if(null != tokens[2] && "Child".equalsIgnoreCase(tokens[2])){
			Integer noOfChildren = (null == tokens[3] || tokens[3].trim().isEmpty() ) ? 0: Integer.valueOf(tokens[3].trim());
			guests.put("Child", noOfChildren);
		}
		voucherDetails.getGuestsPerRoom().put("Room1", guests);
		//voucherDetails.detailsMap.get("Special Request");
		//voucherDetails.setInclusions(inclusions);detailsMap.get("inclusions");
		voucherDetails.setBookingType(detailsMap.get("Booking Type"));
		
		return voucherDetails;
	}

	private static String correctDateString(String dateStr) {
		dateStr = dateStr.replace(".", "");
		if(dateStr.startsWith("Sept")) {
			dateStr = dateStr.replace("Sept", "Sep");
		} else if(dateStr.startsWith("June")) {
			dateStr = dateStr.replace("June", "Jun");
		} else if(dateStr.startsWith("July")) {
			dateStr = dateStr.replace("July", "Jul");
		}
		return dateStr;
	}

}
