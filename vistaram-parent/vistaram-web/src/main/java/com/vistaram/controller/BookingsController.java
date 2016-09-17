package com.vistaram.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.EntityToDtoMapper;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.service.BookingDetailsService;

@RestController
public class BookingsController {
	
	private static Logger logger = LoggerFactory.getLogger(BookingsController.class);
	
	
	
	@Autowired
	private BookingDetailsService bookingDetailsService;
	
	@RequestMapping("/bookings")
	public List<VoucherDetail> getBookings(){
		logger.debug("getBookings()-->");
		List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();
		List<BookingDetail> bookingDetails = bookingDetailsService.getBookingDetailsByBookingDate(new Date());
		logger.debug("no. of bookings : "+bookingDetails.size());
		for(BookingDetail bookingDetail : bookingDetails){
			VoucherDetail voucherDetail = EntityToDtoMapper.mapBookingDetailToVoucherDetails(bookingDetail);
			logger.debug("voucher detail : "+voucherDetail);
			voucherDetails.add(voucherDetail);
		}
		logger.debug("<-- getBookings()");
		return voucherDetails;
	}
	
	@RequestMapping("/bookings/today")
	public List<VoucherDetail> getTodaysBookings(){
		logger.debug("getTodaysBookings()-->");
		List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();
		
		List<BookingDetail> bookingDetails = bookingDetailsService.getBookingDetailsForCurrentBookingDate();
		logger.debug("no. of bookings : "+bookingDetails.size());
		for(BookingDetail bookingDetail : bookingDetails){
			VoucherDetail voucherDetail = EntityToDtoMapper.mapBookingDetailToVoucherDetails(bookingDetail);
			logger.debug("voucher detail : "+voucherDetail);
			voucherDetails.add(voucherDetail);
		}
		logger.debug("<-- getTodaysBookings()");
		return voucherDetails;
	}
	
	@RequestMapping("/checkins/today")
	public List<VoucherDetail> getTodaysCheckins(Principal principal){
		logger.debug("getTodaysCheckins()-->");
		List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();
		List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		logger.debug(String.valueOf(authorities));
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
		if(authorities.contains(adminAuthority)){
			bookingDetails = bookingDetailsService.getBookingDetailsForCurrentCheckinDate();
		} else {
			bookingDetails = bookingDetailsService.getBookingDetailsForCurrentCheckinDateForUser(principal.getName());
		}
		logger.debug("no. of bookings : "+bookingDetails.size());
		for(BookingDetail bookingDetail : bookingDetails){
			VoucherDetail voucherDetail = EntityToDtoMapper.mapBookingDetailToVoucherDetails(bookingDetail);
			logger.debug("voucher detail : "+voucherDetail);
			voucherDetails.add(voucherDetail);
		}
		logger.debug("<-- getTodaysCheckins()");
		return voucherDetails;
	}
	
	@RequestMapping("/checkouts/today")
	public List<VoucherDetail> getTodaysCheckouts(Principal principal){
		logger.debug("getTodaysCheckouts()-->");
		List<VoucherDetail> voucherDetails = new ArrayList<VoucherDetail>();
		List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		logger.debug(String.valueOf(authorities));
		SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
		if(authorities.contains(adminAuthority)){
			bookingDetails = bookingDetailsService.getBookingDetailsForCurrentCheckoutDate();
		} else {
			bookingDetails = bookingDetailsService.getBookingDetailsForCurrentCheckoutDateForUser(principal.getName());
		}
		logger.debug("no. of bookings : "+bookingDetails.size());
		for(BookingDetail bookingDetail : bookingDetails){
			VoucherDetail voucherDetail = EntityToDtoMapper.mapBookingDetailToVoucherDetails(bookingDetail);
			logger.debug("voucher detail : "+voucherDetail);
			voucherDetails.add(voucherDetail);
		}
		logger.debug("<-- getTodaysCheckouts()");
		return voucherDetails;
	}
	
	
	@RequestMapping(value = "/download/{voucherNumber}")
	public void generateVoucher(HttpServletResponse response, @PathVariable(value="voucherNumber") String voucherNumber){
		logger.debug("generateVoucher() -->");
		logger.debug("voucherNumber: "+voucherNumber);
		
		BookingDetail bookingDetail = bookingDetailsService.getBookingDetailsByVoucherNumber(voucherNumber);
		
		String hotelName = bookingDetail.getHotelDetail().getHotelName();
		String guestName = bookingDetail.getGuestDetail().getFirstName()+" "+bookingDetail.getGuestDetail().getLastName();
		
		String payment = null;
		System.out.println("payment Type : "+bookingDetail.getPaymentType());
		if(bookingDetail.getPaymentType().equalsIgnoreCase(PaymentType.ONLINE.toString())){
			payment=bookingDetail.getTotalAmount()+" Paid";
		} else {
			payment="Pay @Hotel "+bookingDetail.getTotalAmount();
		}
		
		Date bookingDate = bookingDetail.getBookingDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("IST"));
		String bookingDateStr = format.format(bookingDate);
		Date checkinDate = bookingDetail.getCheckinDate();
		String checkin = format.format(checkinDate);
		Date checkoutDate = bookingDetail.getCheckoutDate();
		String checkout = format.format(checkoutDate);
		int noOfNights = bookingDetail.getNoOfNights();
		int noOfRooms = bookingDetail.getNoOfRooms();
		//ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			java.io.InputStream in = BookingsController.class.getClassLoader().getResourceAsStream("vistaramrooms_layout.pdf");
			PdfReader pdfReader = new PdfReader(in);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, outputStream);
			pdfStamper.getAcroFields().setField("Hotel", hotelName);
			pdfStamper.getAcroFields().setField("GuestName", guestName);
			pdfStamper.getAcroFields().setField("BookingDate", bookingDateStr);
			pdfStamper.getAcroFields().setField("CheckinDate", checkin);
			pdfStamper.getAcroFields().setField("CheckoutDate", checkout);
			pdfStamper.getAcroFields().setField("NoOfNights", ""+noOfNights);
			//pdfStamper.getAcroFields().setField("RoomType", "");
			pdfStamper.getAcroFields().setField("NoOfRooms", ""+noOfRooms);
			//pdfStamper.getAcroFields().setField("RatePlan", "Business Partner");
			pdfStamper.getAcroFields().setField("Payment", payment);
			pdfStamper.close();
			pdfReader.close();
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("<-- generateVoucher()");
		
	}

}
