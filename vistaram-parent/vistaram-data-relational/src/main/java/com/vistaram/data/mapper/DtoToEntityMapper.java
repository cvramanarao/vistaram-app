package com.vistaram.data.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.domain.HotelDetail;
import com.vistaram.data.relational.domain.RoomDetail;

public class DtoToEntityMapper {
	
	
	public static  BookingDetail mapVoucherDetailsToBookingDetails(
			VoucherDetails voucherDetails) {
		
		
		System.out.println("mapVoucherDetailsToBookingDetails()-->");
		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setBookingAgent(voucherDetails.getBookingAgent());
		bookingDetail.setVoucherId(voucherDetails.getVoucherNumber());
		bookingDetail.setBookingDate(voucherDetails.getBookingDate());
		bookingDetail.setCheckinDate(voucherDetails.getCheckInDate());
		
		bookingDetail.setCheckoutDate(voucherDetails.getCheckOutDate());
		bookingDetail.setNoOfRooms(voucherDetails.getNoOfRooms());
		
		bookingDetail.setNoOfNights(voucherDetails.getNoOfNights());
		//bookingDetail.setRatePlanId(voucherDetails.getRatePlan());
		bookingDetail.setRoomDetails(mapVoucherDetailsToRoomDetails(voucherDetails));
		bookingDetail.setGuestDetail(mapVoucherDetailsToGuestDetails(voucherDetails));
//		if(voucherDetails.getBookingType().contains("online")){
//			
//		}
		
		bookingDetail.setPaymentType(PaymentType.ONLINE.toString());
		System.out.println("<-- mapVoucherDetailsToBookingDetails()");
		return bookingDetail;
		
	}
	
	
	
	public static GuestDetail mapVoucherDetailsToGuestDetails(VoucherDetails voucherDetails){
		GuestDetail guestDetail = new GuestDetail();
		String guestName = voucherDetails.getGuestName();
		int beginIndex =  guestName.lastIndexOf(" ");
		if(beginIndex > -1) {
		guestDetail.setFirstName(guestName.substring(0, beginIndex));
		guestDetail.setLastName(guestName.substring(beginIndex));
		} else {
			guestDetail.setFirstName(guestName);
			guestDetail.setLastName(" ");
		}
		
		return guestDetail;
	}
	public static List<RoomDetail> mapVoucherDetailsToRoomDetails(VoucherDetails voucherDetails){
		List<RoomDetail> roomDetails = new ArrayList<RoomDetail>();
		for(int i=0;i<voucherDetails.getNoOfRooms();i++) {
			RoomDetail roomDetail = new RoomDetail();
			
			for(Map.Entry<String, Map<String, Integer>> entry : voucherDetails.getGuestsPerRoom().entrySet()){
				roomDetail.setRoomName(entry.getKey());
				roomDetail.setNoOfAdults(entry.getValue().get("Adult"));
				roomDetail.setNoOfAdults(entry.getValue().get("Child"));
			}
			//roomDetail.setRoomRate(voucherDetails.get);
			roomDetail.setRoomType(voucherDetails.getRoomType());
			roomDetails.add(roomDetail);
		}
		return roomDetails;
		
	}

}
