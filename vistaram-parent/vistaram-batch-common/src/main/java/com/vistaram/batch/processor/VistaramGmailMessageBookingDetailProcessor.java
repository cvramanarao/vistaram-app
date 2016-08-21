package com.vistaram.batch.processor;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	
	

	@Override
	public BookingDetail process(Message message) throws Exception {
		System.out.println("VistaramGmailMessageBookingDetailProcessor || process() -->");
		BookingDetail bookingDetail = null;
		try {
			VoucherDetail voucherDetail = extractVoucherDetail(message);
			
			if(null != voucherDetail) {
				Query query = entityManager.createNamedQuery("BookingDetail.findByVoucherId");
				query.setParameter("voucherId", voucherDetail.getVoucherNumber());
				List results =  query.getResultList();
				System.out.println("booking detail for voucher number "+voucherDetail.getVoucherNumber()+" is "+results);
				if(results.isEmpty()) { 
					bookingDetail = DtoToEntityMapper.mapVoucherDetailsToBookingDetails(voucherDetail);
					String hotelAndCity = voucherDetail.getHotelAndCity();
					String hotel = hotelAndCity.substring(0, hotelAndCity.indexOf(",")).trim();
					System.out.println("hotel : "+hotel);
					HotelDetail hotelDetail = findHotel(hotel, bookingDetail.getBookingAgent());
					System.out.println("Hotel : "+hotelDetail);
					bookingDetail.setHotelDetail(hotelDetail);
				}
			}
			
		} catch(Exception ex){
			ex.printStackTrace(System.out);
		} finally  {
			// TODO Auto-generated catch block
			System.out.println("<-- VistaramGmailMessageBookingDetailProcessor || process()");
		}
		
		return bookingDetail;
	}

	private JobExecution jobExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
		Query query = entityManager.createQuery("select h from HotelDetail h");
		hotels = query.getResultList();
		System.out.println("hotels : "+hotels);
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		stepExecution.getJobExecution().getExecutionContext().remove("service");
		stepExecution.getJobExecution().getExecutionContext().remove("user");
	}

	public VoucherDetail extractVoucherDetail(Message message) throws Exception {
		
		VoucherDetail voucherDetails = null;
		MessagePart payload = message.getPayload();

		// System.out.println("Payload : "+ payload);
		// System.out.println("Raw : "+ message.getRaw());
		// System.out.println(message.toPrettyString());
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
		System.out.println("from : " + from + " subject: " + subject);

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
		//System.out.println(hotelIdentifier+" <--> "+bookingAgent);
		for(HotelDetail hotel : hotels){
			System.out.println(hotel);
			if(hotel.getHotelIdentifierName().equalsIgnoreCase(hotelIdentifier) && hotel.getBookingAgent().equalsIgnoreCase(bookingAgent)){
				return hotel;
			}
		}
		
		return null;
	}

}
