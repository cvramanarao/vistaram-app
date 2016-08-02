package com.vistaram.data.relational.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.HotelDetail;
import com.vistaram.data.relational.repositories.HotelDetailRepository;

@Repository
@Transactional
public class HotelDetailDao {
	
	@Autowired
	private HotelDetailRepository hotelDetailRepo;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public HotelDetail getHotelDetailByIdentificationName(String identifierName){
		return hotelDetailRepo.findHotelDetailByIdentificationName(identifierName);
	}

}
