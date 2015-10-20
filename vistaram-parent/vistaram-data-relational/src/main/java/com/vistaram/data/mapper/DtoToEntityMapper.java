package com.vistaram.data.mapper;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.relational.domain.BookingDetail;

public class DtoToEntityMapper {
	
	
	public static  BookingDetail mapVoucherDetailsToBookingDetails(
			VoucherDetails voucherDetails) {
		
		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setBookingAgent(voucherDetails.getBookingAgent());
		bookingDetail.setVoucherId(voucherDetails.getVoucherNumber());
		bookingDetail.setBookingDate(voucherDetails.getBookingDate());
		bookingDetail.setCheckingDate(voucherDetails.getCheckInDate());
		bookingDetail.setCheckoutDate(voucherDetails.getCheckOutDate());
		
		return bookingDetail;
	}

}
