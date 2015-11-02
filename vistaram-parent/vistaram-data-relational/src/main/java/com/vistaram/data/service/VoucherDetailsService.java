package com.vistaram.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.dao.BookingDetailDao;
import com.vistaram.data.relational.dao.GuestDetailDao;
import com.vistaram.data.relational.dao.HotelDetailDao;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.domain.HotelDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;


@Service
public class VoucherDetailsService {
	
	@Autowired
	private BookingDetailDao bookingDetailDao;
	
	@Autowired
	private HotelDetailDao hotelDetailDao;
	
	@Transactional
	public void saveVoucherDetails(VoucherDetails voucherDetails){
		BookingDetail bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetails);
		HotelDetail hotelDetail = hotelDetailDao.getHotelDetailByIdentificationName(voucherDetails.getHotelAndCity());
		System.out.println("Hotel : "+hotelDetail);
		bookingDetail.setHotelDetail(hotelDetail);
		bookingDetailDao.save(bookingDetail);
	}

}
