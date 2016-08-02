package com.vistaram.data.relational.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;
import com.vistaram.data.relational.repositories.HotelDetailRepository;


@Repository
@Transactional
public class BookingDetailDao {
	
	@Autowired
	private BookingDetailRepository bookingDetailRepo;
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(BookingDetail detail){
		System.out.println("BookingDetailDao || save()-->");
		
		bookingDetailRepo.save(detail);
		System.out.println("<-- BookingDetailDao || save()");
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List<BookingDetail> getBookingDetails(){
		return bookingDetailRepo.findAll();
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public BookingDetail getBookingDetailByVoucherId(String voucherId){
		return bookingDetailRepo.findByVoucherId(voucherId);
	}
}
