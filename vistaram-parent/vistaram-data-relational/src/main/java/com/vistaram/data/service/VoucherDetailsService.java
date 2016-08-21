package com.vistaram.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.dao.BookingDetailDao;
import com.vistaram.data.relational.dao.HotelDetailDao;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.HotelDetail;


@Service
public class VoucherDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(VoucherDetailsService.class);
	
	@Autowired
	private BookingDetailDao bookingDetailDao;
	
	@Autowired
	private HotelDetailDao hotelDetailDao;
	
	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveVoucherDetails(VoucherDetail voucherDetails){
		logger.debug("saveVoucherDetails()-->");
		
		BookingDetail bookingDetail = bookingDetailDao.getBookingDetailByVoucherId(voucherDetails.getVoucherNumber());
		
		if(null == bookingDetail) {
		
			bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetails);
			String hotelAndCity = voucherDetails.getHotelAndCity();
			String bookingAgent = bookingDetail.getBookingAgent();
			logger.debug("hotelAndCity : "+hotelAndCity.substring(0, hotelAndCity.indexOf(","))+" -- "+bookingAgent);
			HotelDetail hotelDetail = hotelDetailDao.getHotelDetail(hotelAndCity.substring(0, hotelAndCity.indexOf(",")), bookingAgent);
			logger.debug("Hotel : "+hotelDetail);
			bookingDetail.setHotelDetail(hotelDetail);
			bookingDetailDao.save(bookingDetail);
		} else {
			
		}
		
		logger.debug("<-- saveVoucherDetails()");
	}

}
