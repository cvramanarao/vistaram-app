package com.vistaram.data.relational.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;


@Repository
@Transactional
public class BookingDetailDao {
	
	@Autowired
	private BookingDetailRepository bookingDetailRepo;
	
	public void save(BookingDetail detail){
		System.out.println("BookingDetailDao || save()-->");
		bookingDetailRepo.save(detail);
		System.out.println("<-- BookingDetailDao || save()");
	}
}
