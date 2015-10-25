package com.vistaram.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.dao.BookingDetailDao;
import com.vistaram.data.relational.dao.GuestDetailDao;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.repositories.BookingDetailRepository;


@Service
public class VoucherDetailsService {
	
	@Autowired
	private BookingDetailDao bookingDetailDao;
	
	@Autowired
	private GuestDetailDao guestDetailDao;
	
	public void saveVoucherDetails(VoucherDetails voucherDetails){
		BookingDetail bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetails);
		
		GuestDetail guestDetail = new GuestDetail();
		String guestName = voucherDetails.getGuestName();
		int beginIndex =  guestName.lastIndexOf(" ");
		if(beginIndex > -1) {
		guestDetail.setFirstName(guestName.substring(0, beginIndex));
		guestDetail.setLastName(guestName.substring(beginIndex));
		} else {
			guestDetail.setFirstName(guestName);
			guestDetail.setLastName(" ");
		}
		guestDetail = guestDetailDao.save(guestDetail);
		bookingDetail.setGuestId(guestDetail.getId());
		bookingDetailDao.save(bookingDetail);
	}


	
	
	
	public void saveVoucherDetails(){
		
	}

}
