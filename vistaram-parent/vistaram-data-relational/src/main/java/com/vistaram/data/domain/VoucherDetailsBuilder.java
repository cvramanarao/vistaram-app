package com.vistaram.data.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherDetailsBuilder {
	
	public static VoucherDetails buildVoucherDetails(Map<String, String> detailsMap){
		VoucherDetails voucherDetails = new VoucherDetails();
		/*
		 * 
		 * {name of hotel & city=Vistaram Rtc Complex, Visakhapatnam, India, type of room=Royal King A/c, number of rooms=1, 
		 * number of nights=1, Rate plan name=MAP, booking date=Oct. 10, 2015, 9:40 p.m., guest request=WEB SERVICES BOOKING, 
		 * room 1=Adult 2 Child 0, Special Request=None, checkout date=Oct. 12, 2015, 
		 * name of guest=Santosh Pradhan, checkin date=Oct. 11, 2015, inclusions=Accommodation , Breakfast and Dinner(MAP), Booking Type=mobile}
		 */
		
		voucherDetails.setVoucherNumber(detailsMap.get("voucher number"));
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
		String hotelAndCity = detailsMap.get("name of hotel & city");
		String[] args = hotelAndCity .split(",");
		if(args.length> 2) {
			hotelAndCity = args[0].trim()+", "+args[1].trim();
		}
		
		voucherDetails.setHotelAndCity(hotelAndCity);
		voucherDetails.setRoomType(detailsMap.get("type of room"));
		voucherDetails.setNoOfRooms(Integer.valueOf(detailsMap.get("number of rooms")));
		voucherDetails.setNoOfNights(Integer.valueOf(detailsMap.get("number of nights")));
		voucherDetails.setRatePlan(detailsMap.get("rate plan name"));
		voucherDetails.setGuestRequest(detailsMap.get("guest request"));
		
	    for(int i=1;i<=voucherDetails.getNoOfRooms();i++) {
			String room1Occupancy = detailsMap.get("room "+i);
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
			voucherDetails.getGuestsPerRoom().put("Room"+i, guests);
		
	    }
		
		//voucherDetails.detailsMap.get("Special Request");
		//voucherDetails.setInclusions(inclusions);detailsMap.get("inclusions");
	    String totalTaxStr = detailsMap.get("total tax");
	    voucherDetails.setTotalTax(null==totalTaxStr || totalTaxStr.isEmpty()?0:Double.valueOf(totalTaxStr));
	    String totalDiscountStr = detailsMap.get("total discount");
	    voucherDetails.setTotalDiscount(null==totalDiscountStr || totalDiscountStr.isEmpty()?0:Double.valueOf(totalDiscountStr));
	    String totalAmountPayableStr = detailsMap.get("total amount payable");
	    voucherDetails.setTotalAmountPayable(null==totalAmountPayableStr || totalAmountPayableStr.isEmpty()?0:Double.valueOf(totalAmountPayableStr));
	    //String extraGuest = detailsMap.get("extra guest");
	    //voucherDetails.setExtraGuest(Integer.valueOf(extraGuest));
	    
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

	
	public static TariffDetails buildTariffDetails(Map<String, String> tariffDetailsMap) {
		TariffDetails tariffDetails = new TariffDetails();
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		String checkInDateStr = tariffDetailsMap.get("check in date");
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
		
		tariffDetails.setCheckInDate(checkInDate);
		
		
		String checkOutDateStr = tariffDetailsMap.get("check out date");
		checkOutDateStr = correctDateString(checkOutDateStr);
		System.out.println("checkOutDateStr: "+checkOutDateStr);
		Date checkOutDate = null;
		try {
			checkOutDate = sdf.parse(checkOutDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tariffDetails.setCheckOutDate(checkOutDate);
		String currency = tariffDetailsMap.get("currency");
		tariffDetails.setCurrency(currency);
		int noOfNights = Integer.valueOf(tariffDetailsMap.get("#nights"));
		tariffDetails.setNoOfNights(noOfNights);
		double ratePerRoom = Double.valueOf(tariffDetailsMap.get("rate per room"));
		tariffDetails.setRatePerRoom(ratePerRoom);
		double roomRate = Double.valueOf(tariffDetailsMap.get("room rate"));
		tariffDetails.setRoomRate(roomRate);
		return tariffDetails;
	}
	
	public static VoucherDetails build(Map<String, String> voucherDetailsMap,
			List<Map<String, String>> tariffDetailsList) {
		VoucherDetails voucherDetails = buildVoucherDetails(voucherDetailsMap);
		
		List<TariffDetails> tariffDetailObjectsList = new ArrayList<TariffDetails>();
		for(Map<String, String> tariffDetailsMap : tariffDetailsList) {
			tariffDetailObjectsList.add(buildTariffDetails(tariffDetailsMap));
		}
		
		voucherDetails.setTariffDetails(tariffDetailObjectsList);
		return voucherDetails;
	}

}
