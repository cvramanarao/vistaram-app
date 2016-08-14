package com.vistaram.data.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeMyTripVoucherDetailsBuilder extends VoucherDetailsBuilder {
	
	@Override
	public  VoucherDetail build(Map<String, String> voucherDetailMap,
			List<Map<String, String>> tariffDetailsList) {
		VoucherDetail voucherDetail = buildvoucherDetail(voucherDetailMap);
		
		/*List<TariffDetails> tariffDetailObjectsList = new ArrayList<TariffDetails>();
		for(Map<String, String> tariffvoucherDetailMap : tariffDetailsList) {
			tariffDetailObjectsList.add(buildTariffDetails(tariffvoucherDetailMap));
		}*/
		
		//voucherDetail.setTariffDetails(tariffDetailObjectsList);
		return voucherDetail;
	}
	
	/*
	 * {BookingDate=Thu, 11 August 16, Tds=0.00, VoucherNumber=NH7102628543447, Check In=Fri, Aug 12, 2016 ( 12:00 PM ), Check Out=Sun, Aug 14, 2016 ( 12:00 PM ), 
	 * Extra Charges=(No Extra Charges), SubTotal=3483.30, GuestContact=919811682368, #Nights=2, RoomType=*Standard 1 Queen, Taxes=532.74, Daily Rate=1741.65, Total=3483.30, 
	 * GrandTotal=4016.04, Travelers=1 Adult, GuestName=Jesunath K D V, GuestEmail=suresh.allada2@gmail.com, 
	 * Room=Room 1, HotelAndCity=Vistaram Rtc Complex , VISHAKHAPATNAM, mealPlan= Room Only}
	 */
	private VoucherDetail buildvoucherDetail(Map<String, String> voucherDetailMap){
		System.out.println("MakeMyTripVoucherBuilder || buildvoucherDetail()-->");
		VoucherDetail voucherDetail = new VoucherDetail();
		
		voucherDetail.setVoucherNumber(voucherDetailMap.get("VoucherNumber"));
		voucherDetail.setGuestName(voucherDetailMap.get("GuestName"));
		//TODO: set Guest Contacts
		
		String bookingDateStr = voucherDetailMap.get("BookingDate");
		//bookingDateStr = correctDateString(bookingDateStr);
		System.out.println("bookingDateStr: "+bookingDateStr);
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date bookingDate = null;
		try {
			//Thu, 11 August 16
			sdf.applyLocalizedPattern("E, dd MMMM yy");
			bookingDate = sdf.parse(bookingDateStr); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("Error parsing booking date : "+bookingDateStr);
		}
		
		voucherDetail.setBookingDate(bookingDate);
		
		String checkInDateStr = voucherDetailMap.get("Check In");
		
		checkInDateStr = checkInDateStr.substring(0, checkInDateStr.indexOf("(")).trim();
		
		//checkInDateStr = correctDateString(checkInDateStr);
		System.out.println("checkInDateStr: "+checkInDateStr);
		Date checkInDate = null;
		try {
			sdf.applyLocalizedPattern("E, MMM dd, yyyy");
		    checkInDate = sdf.parse(checkInDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetail.setCheckInDate(checkInDate);
		
		
		String checkOutDateStr = voucherDetailMap.get("Check Out");
		//checkOutDateStr = correctDateString(checkOutDateStr);
		//System.out.println("checkOutDateStr: "+checkOutDateStr);
		checkOutDateStr = checkOutDateStr.substring(0, checkOutDateStr.indexOf("(")).trim();
		
		Date checkOutDate = null;
		try {
			checkOutDate = sdf.parse(checkOutDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		voucherDetail.setCheckOutDate(checkOutDate);
		String hotelAndCity = voucherDetailMap.get("HotelAndCity");
		String[] args = hotelAndCity .split(",");
		if(args.length> 2) {
			hotelAndCity = args[0].trim()+", "+args[1].trim();
		}
		
		voucherDetail.setHotelAndCity(hotelAndCity);
		voucherDetail.setRoomType(voucherDetailMap.get("RoomType"));
		
		voucherDetail.setNoOfRooms(1);
		Integer noOfNights = Integer.valueOf(voucherDetailMap.get("#Nights"));
		voucherDetail.setNoOfNights(noOfNights);
		voucherDetail.setRatePlan(voucherDetailMap.get("RatePlan"));
		voucherDetail.setGuestRequest(voucherDetailMap.get("guest request"));
		
		String guestsString = voucherDetailMap.get("Travelers");
		Map<String, Integer> guests = new HashMap<String, Integer>();
		//Travelers=2 Adults, 1 Children (3 years)
		String[] guestTokens = guestsString.split(", ");
		System.out.println(Arrays.asList(guestTokens));
		System.out.println(guestTokens[0].trim().charAt(0));
		
		guests.put("Adult", Integer.valueOf(guestTokens[0].trim().charAt(0)));
		if (guestTokens.length > 1) {
			System.out.println(guestTokens[1].trim().charAt(0));
			guests.put("Child", Integer.valueOf(guestTokens[1].trim().charAt(0)));
		}
		voucherDetail.getGuestsPerRoom().put(voucherDetailMap.get("Room"), guests);
		
	    /*for(int i=1;i<=voucherDetail.getNoOfRooms();i++) {
			String room1Occupancy = voucherDetailMap.get("room "+i);
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
			voucherDetail.getGuestsPerRoom().put("Room"+i, guests);
		
	    }*/
		
		//voucherDetail.voucherDetailMap.get("Special Request");
		//voucherDetail.setInclusions(inclusions);voucherDetailMap.get("inclusions");
	    String totalTaxStr = voucherDetailMap.get("Tax");
	    voucherDetail.setTotalTax(null==totalTaxStr || totalTaxStr.isEmpty()?0:Double.valueOf(totalTaxStr));
	    String totalDiscountStr = voucherDetailMap.get("Tds");
	    voucherDetail.setTotalDiscount(null==totalDiscountStr || totalDiscountStr.isEmpty()?0:Double.valueOf(totalDiscountStr));
	    String totalAmountPayableStr = voucherDetailMap.get("GrandTotal");
	    voucherDetail.setTotalAmountPayable(null==totalAmountPayableStr || totalAmountPayableStr.isEmpty()?0:Double.valueOf(totalAmountPayableStr));
	    //String extraGuest = voucherDetailMap.get("extra guest");
	    //voucherDetail.setExtraGuest(Integer.valueOf(extraGuest));
	    
	    List<TariffDetails> tariffDetails = new ArrayList<TariffDetails>();
	    TariffDetails tariffDetail = new TariffDetails();
	    tariffDetail.setCheckInDate(checkInDate);
	    tariffDetail.setCheckOutDate(checkOutDate);
	    tariffDetail.setNoOfNights(noOfNights);
	    //tariffDetail.setRoomRate(roomRate);
	    tariffDetails.add(tariffDetail);
		voucherDetail.setTariffDetails(tariffDetails);
	    
		voucherDetail.setBookingType(voucherDetailMap.get("Booking Type"));	
		
		System.out.println("<-- MakeMyTripVoucherBuilder || buildvoucherDetail()");
		return voucherDetail;
	}

}
