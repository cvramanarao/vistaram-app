package com.vistaram.data.mapper;

import java.util.ArrayList;
import java.util.List;

import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.domain.RoomDetail;
import com.vistaram.data.relational.domain.TariffDetail;

public class EntityToDtoMapper {
	
	public static  VoucherDetail mapBookingDetailToVoucherDetails(
			BookingDetail bookingDetail) {
		
		VoucherDetail voucherDetails = new VoucherDetail();
		System.out.println("mapBookingDetailToVoucherDetails()-->");
		voucherDetails.setVoucherNumber(bookingDetail.getVoucherId());
		voucherDetails.setBookingDate(bookingDetail.getBookingDate());
		voucherDetails.setCheckInDate(bookingDetail.getCheckinDate());
		voucherDetails.setCheckOutDate(bookingDetail.getCheckoutDate());
		voucherDetails.setTotalAmountPayable(bookingDetail.getTotalAmout());
		voucherDetails.setGuestName(bookingDetail.getGuestDetail().getLastName()+", "+bookingDetail.getGuestDetail().getFirstName());
		voucherDetails.setHotelAndCity(bookingDetail.getHotelDetail().getHotelName());
		voucherDetails.setNoOfRooms(bookingDetail.getNoOfRooms());
		voucherDetails.setNoOfNights(bookingDetail.getNoOfNights());
		System.out.println("<-- mapBookingDetailToVoucherDetails()");
		return voucherDetails;
		
	}

}
