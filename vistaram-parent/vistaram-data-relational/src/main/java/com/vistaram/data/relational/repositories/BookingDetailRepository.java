package com.vistaram.data.relational.repositories;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.BookingDetail;
//com.vistaram.data.relational.repositories.BookingDetailRepository

//@Transactional(readOnly=false)
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Long> {
	
	@Query("select b from BookingDetail b where b.voucherId = :voucherId")
	public BookingDetail findByVoucherId(@Param("voucherId") String voucherId);

	@Query("select b from BookingDetail b where b.bookingDate >= :startDate and b.bookingDate <= :endDate order by bookingDate desc")
	public List<BookingDetail> findByBookingDateByRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select b from BookingDetail b where b.checkinDate >= :startDate and b.checkinDate <= :endDate")
	public List<BookingDetail> findByCheckinDateByRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select b from BookingDetail b where b.checkoutDate >= :startDate and b.checkoutDate <= :endDate")
	public List<BookingDetail> findByCheckoutDateByRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("select b from BookingDetail b where b.bookingDate >= :startDate")
	public List<BookingDetail> findByCurrentBookingDate(@Param("startDate") Date startDate);

	@Query("select b from BookingDetail b where b.checkoutDate >= :startDate and b.checkinDate <= :startDate")
	public List<BookingDetail> findByCurrentCheckInDate(@Param("startDate")Date startDate);

	@Query("select b from BookingDetail b where b.checkoutDate >= :startDate and b.checkinDate <= :startDate and b.hotelDetail.user.username = :user")
	public List<BookingDetail> findByCurrentCheckInDateForUser(@Param("startDate") Date startDate, @Param("user") String hotelier);
	
	@Query("select b from BookingDetail b where b.hotelDetail.user.username = :user and b.checkoutDate >= :startDate and b.checkoutDate <= :endDate")
	public List<BookingDetail> findByCurrentCheckOutDateForUser(@Param("startDate") Date startDate, @Param("user") String hotelier);

	@Query("select b from BookingDetail b where b.checkoutDate >= :startDate")
	public List<BookingDetail> findByCurrentCheckOutDate(@Param("startDate")Date startDate);
	
}
