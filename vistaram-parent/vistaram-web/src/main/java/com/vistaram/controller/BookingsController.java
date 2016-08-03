package com.vistaram.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.EntityToDtoMapper;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.service.BookingDetailsService;
import com.vistaram.data.service.VoucherDetailsService;

@RestController
public class BookingsController {
	
	
	@Autowired
	private BookingDetailsService bookingDetailsService;
	
	@RequestMapping("/bookings")
	public List<VoucherDetail> getBookings(){
		System.out.println("getBookings()-->");
		List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();
		List<BookingDetail> bookingDetails = bookingDetailsService.getBookingDetailsByBookingDate(new Date());
		System.out.println("no. of bookings : "+bookingDetails.size());
		for(BookingDetail bookingDetail : bookingDetails){
			VoucherDetail voucherDetail = EntityToDtoMapper.mapBookingDetailToVoucherDetails(bookingDetail);
			voucherDetails.add(voucherDetail);
		}
		System.out.println("<-- getBookings()");
		return voucherDetails;
	}

}
