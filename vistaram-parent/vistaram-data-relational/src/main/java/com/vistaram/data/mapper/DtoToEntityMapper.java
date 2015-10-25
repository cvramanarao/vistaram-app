package com.vistaram.data.mapper;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.relational.domain.BookingDetail;

public class DtoToEntityMapper {
	
	
	public static  BookingDetail mapVoucherDetailsToBookingDetails(
			VoucherDetails voucherDetails) {
		
		
		System.out.println("mapVoucherDetailsToBookingDetails()-->");
		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setBookingAgent(voucherDetails.getBookingAgent());
		bookingDetail.setVoucherId(voucherDetails.getVoucherNumber());
		bookingDetail.setBookingDate(voucherDetails.getBookingDate());
		bookingDetail.setCheckingDate(voucherDetails.getCheckInDate());
		bookingDetail.setCheckoutDate(voucherDetails.getCheckOutDate());
		bookingDetail.setNumberOfRooms(voucherDetails.getNoOfRooms());
		bookingDetail.setNoOfNightsStayed(voucherDetails.getNoOfNights());
		//bookingDetail.setRatePlanId(voucherDetails.getRatePlan());
		
		System.out.println("<-- mapVoucherDetailsToBookingDetails()");
		return bookingDetail;
		
	}

}
