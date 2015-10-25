package com.vistaram.data.relational.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.repositories.GuestDetailRepository;

@Repository
@Transactional
public class GuestDetailDao {
	
	@Autowired
	private GuestDetailRepository repo;
	
	public GuestDetail save(GuestDetail guestDetail) {
		GuestDetail detail = repo.save(guestDetail);
		return detail;
	}

}
