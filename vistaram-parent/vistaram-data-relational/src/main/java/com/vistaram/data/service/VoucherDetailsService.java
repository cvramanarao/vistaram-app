package com.vistaram.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.dao.BookingDetailDao;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;


@Service
public class VoucherDetailsService {
	
	@Autowired
	private BookingDetailDao bookingDetailDao;
	
	public void saveVoucherDetails(VoucherDetails voucherDetails){
		BookingDetail bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetails);
		bookingDetailDao.save(bookingDetail);
	}


	
	
	
	public void saveVoucherDetails(){
		
	}

}
