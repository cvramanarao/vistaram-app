package com.vistaram.data.mapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
		//System.out.println("mapBookingDetailToVoucherDetails()-->");
		voucherDetails.setVoucherNumber(bookingDetail.getVoucherId());
		Calendar bookingCl = Calendar.getInstance();
		bookingCl.setTime(bookingDetail.getBookingDate());
		
		Calendar cl = Calendar.getInstance();
		cl.setTimeZone(TimeZone.getTimeZone("IST"));
		cl.set(bookingCl.get(Calendar.YEAR), bookingCl.get(Calendar.MONTH), bookingCl.get(Calendar.DATE), bookingCl.get(Calendar.HOUR_OF_DAY), bookingCl.get(Calendar.MINUTE), bookingCl.get(Calendar.SECOND));
		voucherDetails.setBookingDate(cl.getTime());
		voucherDetails.setCheckInDate(bookingDetail.getCheckinDate());
		voucherDetails.setCheckOutDate(bookingDetail.getCheckoutDate());
		voucherDetails.setTotalAmountPayable(bookingDetail.getTotalAmount());
		voucherDetails.setGuestName(bookingDetail.getGuestDetail().getLastName()+", "+bookingDetail.getGuestDetail().getFirstName());
		voucherDetails.setHotelAndCity(bookingDetail.getHotelDetail().getHotelName());
		voucherDetails.setNoOfRooms(bookingDetail.getNoOfRooms());
		voucherDetails.setNoOfNights(bookingDetail.getNoOfNights());
		voucherDetails.setBookingAgent(bookingDetail.getBookingAgent());
		voucherDetails.setSource(bookingDetail.getSource());
		//System.out.println("<-- mapBookingDetailToVoucherDetails()");
		return voucherDetails;
		
	}

}
