package com.vistaram.data.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vistaram.data.relational.dao.BookingDetailDao;
import com.vistaram.data.relational.domain.BookingDetail;


@Service
public class BookingDetailsService {
	@Autowired
	private BookingDetailDao bookingDetailDao;
	
	public List<BookingDetail> getBookingDetailsByBookingDate(Date date) {
		return bookingDetailDao.getBookingDetails(date);
	}
	
	public List<BookingDetail> getBookingDetailsForCurrentBookingDate() {
		
		return bookingDetailDao.getCurrentDateBookingDetails();
	}

}
