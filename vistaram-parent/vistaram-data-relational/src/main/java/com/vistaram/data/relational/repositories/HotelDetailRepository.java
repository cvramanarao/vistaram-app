package com.vistaram.data.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vistaram.data.relational.domain.HotelDetail;

public interface HotelDetailRepository extends JpaRepository<HotelDetail, Integer> {

	@Query("select h from HotelDetail h where h.hotelIdentifierName = :hotelIdentifierName")
	public HotelDetail findHotelDetailByIdentificationName(@Param("hotelIdentifierName") String identifierName);

	@Query("select h from HotelDetail h where h.hotelIdentifierName = :hotelIdentifierName and h.bookingAgent = :bookingAgent")
	public HotelDetail findHotelDetail(@Param("hotelIdentifierName") String identifierName, @Param("bookingAgent") String bookingAgent);
}
