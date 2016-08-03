package com.vistaram.data.relational.repositories;



import java.util.Date;
import java.util.List;

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

	@Query("select b from BookingDetail b where b.bookingDate > :startDate and b.bookingDate < :endDate ")
	public List<BookingDetail> findByBookingDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select b from BookingDetail b where b.checkinDate > :startDate and b.checkinDate < :endDate ")
	public List<BookingDetail> findByCheckinDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select b from BookingDetail b where b.checkoutDate > :startDate and b.checkoutDate < :endDate ")
	public List<BookingDetail> findByCheckoutDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
}
