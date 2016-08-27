package com.vistaram.batch.processor;

import java.util.List;

import javax.mail.Message;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.HotelDetail;


public class VistaramEmailMessageBookingDetailProcessor implements ItemProcessor<Message, BookingDetail>{
	
	
	@Autowired
	private EntityManager entityManager;
	
	private static List<HotelDetail> hotels = null;
	
	
	private VistaramEmailMessageProcessor processor = new VistaramEmailMessageProcessor();
	
	private static Logger logger = LoggerFactory.getLogger(VistaramEmailMessageBookingDetailProcessor.class);

	private JobExecution jobExecution;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		logger.debug("beforeStep()-->");
		jobExecution = stepExecution.getJobExecution();
		logger.debug("entityManager :- "+entityManager);
		
		Query query = entityManager.createQuery("select h from HotelDetail h");
		hotels = query.getResultList();
		logger.debug("hotels : "+hotels);
		logger.debug("<-- beforeStep()");
	}
	
	@Override
	public BookingDetail process(Message message) throws Exception {
		logger.debug("VistaramEmailMessageBookingDetailProcessor || process(Message message) -->");
		BookingDetail bookingDetail = null;
		
		VoucherDetail voucherDetail = processor.process(message);
		if(null != voucherDetail) {
			Query query = entityManager.createNamedQuery("BookingDetail.findByVoucherId");
			query.setParameter("voucherId", voucherDetail.getVoucherNumber());
			List results =  query.getResultList();
			logger.debug("booking detail for voucher number "+voucherDetail.getVoucherNumber()+" is "+results);
			if(results.isEmpty()) { 
				bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetail);
				String hotelAndCity = voucherDetail.getHotelAndCity();
				String hotel = hotelAndCity.substring(0, hotelAndCity.indexOf(",")).trim();
				logger.debug("hotel : "+hotel);
				HotelDetail hotelDetail = findHotel(hotel, bookingDetail.getBookingAgent());
				logger.debug("HotelDetail : "+hotelDetail);
				bookingDetail.setHotelDetail(hotelDetail);
			}
		}
		
		logger.debug("<-- VistaramEmailMessageBookingDetailProcessor || process(Message message)");
		return bookingDetail;
	}
	
	private HotelDetail findHotel(String hotelIdentifier, String bookingAgent){
		logger.debug(hotelIdentifier+" <--> "+bookingAgent);
		if(null == hotels){
			Query query = entityManager.createQuery("select h from HotelDetail h");
			hotels = query.getResultList();
			logger.debug("hotels: "+hotels);
		}
		for(HotelDetail hotel : hotels){
			//logger.debug(hotel);
			if(hotel.getHotelIdentifierName().equalsIgnoreCase(hotelIdentifier) && hotel.getBookingAgent().equalsIgnoreCase(bookingAgent)){
				return hotel;
			}
		}
		
		return null;
	}
}
