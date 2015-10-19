package com.vistaram.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vistaram.relational.domain.BookingDetail;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
	
}
