package com.vistaram.data.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vistaram.data.relational.domain.BookingDetail;
//com.vistaram.data.relational.repositories.BookingDetailRepository
@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
	
}
