package com.vistaram.batch.processor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.HotelDetail;


public class VistaramGmailMessageBookingDetailProcessor implements
		ItemProcessor<Message, BookingDetail> {
	
	@Autowired
	private EntityManager entityManager;
	
	private List<HotelDetail> hotels = null;
	
	private static Logger logger = LoggerFactory
			.getLogger(VistaramGmailMessageBookingDetailProcessor.class);

	@Override
	public BookingDetail process(Message message) throws Exception {
		logger.debug("VistaramGmailMessageBookingDetailProcessor || process() -->");
		BookingDetail bookingDetail = null;
		try {
			VoucherDetail voucherDetail = extractVoucherDetail(message);
			
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
					logger.debug("Hotel : "+hotelDetail);
					bookingDetail.setHotelDetail(hotelDetail);
				}
			}
			
		} catch(Exception ex){
			ex.printStackTrace(System.out);
		} finally  {
			// TODO Auto-generated catch block
			logger.debug("<-- VistaramGmailMessageBookingDetailProcessor || process()");
		}
		
		return bookingDetail;
	}

	private JobExecution jobExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
		Query query = entityManager.createQuery("select h from HotelDetail h");
		hotels = query.getResultList();
		logger.debug("hotels : "+hotels);
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		stepExecution.getJobExecution().getExecutionContext().remove("service");
		stepExecution.getJobExecution().getExecutionContext().remove("user");
	}

	public VoucherDetail extractVoucherDetail(Message message) throws Exception {
		
		VoucherDetail voucherDetails = null;
		MessagePart payload = message.getPayload();

		// logger.debug("Payload : "+ payload);
		// logger.debug("Raw : "+ message.getRaw());
		// logger.debug(message.toPrettyString());
		Gmail service = (Gmail) jobExecution.getExecutionContext().get(
				"service");
		String user = String.valueOf(jobExecution.getJobParameters().getString(
				"user"));

		Message msg = service.users().messages().get(user, message.getId())
				.execute();

		MessagePart payload2 = msg.getPayload();

		List<MessagePartHeader> headers = payload2.getHeaders();

		String from = "";
		String subject = "";
		for (MessagePartHeader header : headers) {

			if (header.getName().equalsIgnoreCase("from")) {
				from = header.getValue();
			}

			if (header.getName().equalsIgnoreCase("subject")) {
				subject = header.getValue();
			}
		}
		logger.debug("from : " + from + " subject: " + subject);

		if (from.contains("hotelpartners@goibibo.com")
				&& subject.contains("Confirm Hotel Booking")) {
			String data = null;
			if(payload2.getParts().size() > 1) {
				data = new String(Base64.decodeBase64(payload2.getParts()
					.get(1).getBody().getData()));
			} else {
				data = new String(Base64.decodeBase64(payload2.getParts()
						.get(0).getBody().getData()));
			}
			voucherDetails = VistaramMessageUtils
					.extractGoIbiboVoucherDetailsFromHtml(null, from, data);
		}
		
		if(from.equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>")
				  && subject.contains("Hotel Booking on MakeMyTrip.com")) { 
			String data = new String(Base64.decodeBase64(payload2.getParts()
					.get(0).getBody().getData()));
			
			   voucherDetails = VistaramMessageUtils.extractMakeMyTripVoucherDetailsFromHtml(null, from, data);
		  }
		
		
		
		
		return voucherDetails;
	}
	
	private HotelDetail findHotel(String hotelIdentifier, String bookingAgent){
		//logger.debug(hotelIdentifier+" <--> "+bookingAgent);
		if (null == hotels) {
			Query query = entityManager
					.createQuery("select h from HotelDetail h");
			hotels = query.getResultList();
			logger.debug("hotels : " + hotels);
		}
		for(HotelDetail hotel : hotels){
			logger.debug(String.valueOf(hotel));
			if(hotel.getHotelIdentifierName().equalsIgnoreCase(hotelIdentifier) && hotel.getBookingAgent().equalsIgnoreCase(bookingAgent)){
				return hotel;
			}
		}
		
		return null;
	}
	
	
	public void setJobExecution(JobExecution jobExecution){
		this.jobExecution = jobExecution;
	}

}
