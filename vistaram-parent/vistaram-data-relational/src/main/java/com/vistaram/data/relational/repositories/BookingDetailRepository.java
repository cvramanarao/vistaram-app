package com.vistaram.data.relational.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.BookingDetail;
//com.vistaram.data.relational.repositories.BookingDetailRepository

@Transactional(readOnly=false)
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
	
	@Query("select b from BookingDetail b where b.voucherId = :voucherId")
	public BookingDetail findByVoucherId(@Param("voucherId") String voucherId);
	
}
