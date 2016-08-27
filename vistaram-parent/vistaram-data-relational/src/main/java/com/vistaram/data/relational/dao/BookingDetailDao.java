package com.vistaram.data.relational.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;


@Repository
//@Transactional
public class BookingDetailDao {
	
	@Autowired
	private BookingDetailRepository bookingDetailRepo;
	
	
	
	//@Transactional(propagation=Propagation.REQUIRED)
	public void save(BookingDetail detail){
		System.out.println("BookingDetailDao || save()-->");
		
		bookingDetailRepo.saveAndFlush(detail);
		System.out.println("<-- BookingDetailDao || save()");
	}
	
	//@Transactional(propagation=Propagation.REQUIRED)
	public List<BookingDetail> getBookingDetails(){
		return bookingDetailRepo.findAll();
	}
	
	//@Transactional(propagation=Propagation.REQUIRED)
	public List<BookingDetail> getCurrentDateBookingDetails(){
		Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		System.out.println("Current Date : "+cl.getTime());
		return bookingDetailRepo.findByCurrentBookingDate(cl.getTime());
	}
	
	//@Transactional(propagation=Propagation.REQUIRED)
	public List<BookingDetail> getBookingDetails(Date date){
		
		Calendar begin = Calendar.getInstance();
		begin.setTimeZone(TimeZone.getTimeZone("IST"));
		begin.setTime(date);
		System.out.println(begin.getTimeZone());
		//Date end = cl.getTime();
		begin.set(Calendar.HOUR_OF_DAY, 0);
		begin.set(Calendar.MINUTE, 0);
		begin.set(Calendar.SECOND, 0);
		begin.set(Calendar.MILLISECOND, 0);
		
		System.out.println("begin: "+begin);
		
		Calendar end = Calendar.getInstance();
		end.setTimeZone(TimeZone.getTimeZone("IST"));
		end.setTime(date);
		System.out.println(end.getTimeZone());
		//Date end = cl.getTime();
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		System.out.println("end : "+end);
		return bookingDetailRepo.findByBookingDateByRange(begin.getTime(), end.getTime());
	}
	
	//@Transactional(propagation=Propagation.REQUIRED)
	public BookingDetail getBookingDetailByVoucherId(String voucherId){
		return bookingDetailRepo.findByVoucherId(voucherId);
	}

	public List<BookingDetail> getCurrentDateCheckInDetails() {
		// TODO Auto-generated method stub
		Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		
		return bookingDetailRepo.findByCurrentCheckInDate(cl.getTime());
	}

	public List<BookingDetail> getCurrentDateCheckInDetailsForUser(String name) {
		// TODO Auto-generated method stub
		Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		
		return bookingDetailRepo.findByCurrentCheckInDateForUser(cl.getTime(), name);
	}

	public List<BookingDetail> getCurrentDateCheckOutDetails() {
		Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		
		Calendar cl1 = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl1.set(Calendar.HOUR_OF_DAY, 23);
		cl1.set(Calendar.MINUTE, 59);
		cl1.set(Calendar.SECOND, 59);
		cl1.set(Calendar.MILLISECOND, 999);
		
		return bookingDetailRepo.findByCheckoutDateByRange(cl.getTime(), cl1.getTime());
	}

	public List<BookingDetail> getCurrentDateCheckOutDetailsForUser(String name) {
		Calendar cl = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);
		
		Calendar cl1 = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cl1.set(Calendar.HOUR_OF_DAY, 23);
		cl1.set(Calendar.MINUTE, 59);
		cl1.set(Calendar.SECOND, 59);
		cl1.set(Calendar.MILLISECOND, 999);
		
		return bookingDetailRepo.findByCheckoutDateByRange(cl.getTime(), cl1.getTime());
		
		//return bookingDetailRepo.findByCurrentCheckOutDateForUser(cl.getTime(),cl1.getTime(), name);
	}
}
