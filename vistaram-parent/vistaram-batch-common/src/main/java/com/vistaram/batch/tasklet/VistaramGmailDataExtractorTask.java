package com.vistaram.batch.tasklet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.vistaram.batch.processor.VistaramGmailMessageBookingDetailProcessor;
import com.vistaram.batch.utils.GmailUtils;
import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.mapper.DtoToEntityMapper;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.HotelDetail;

public class VistaramGmailDataExtractorTask implements Tasklet {

	private JobExecution jobExecution;

	private static Logger logger = LoggerFactory
			.getLogger(VistaramGmailDataExtractorTask.class);

	private static List<HotelDetail> hotels;

	@Autowired
	private EntityManager entityManager;
	//
	// @Value("#{jobParameters}")
	// private Map<String, JobParameter> jobParameters;

	@Autowired
	private VistaramGmailMessageBookingDetailProcessor vistaramGmailMessageBookingDetailProcessor;

	@Autowired
	private ItemWriter<BookingDetail> bookingDetailWriter;

	@Override
	public RepeatStatus execute(StepContribution stepContribution,
			ChunkContext chunkContext) throws Exception {
		// logger.debug("jobParameters : "+jobParameters);

		if (null == hotels) {
			Query query = entityManager
					.createQuery("select h from HotelDetail h");
			hotels = query.getResultList();
			logger.debug("hotels : " + hotels);
		}
		jobExecution = chunkContext.getStepContext().getStepExecution()
				.getJobExecution();

		logger.debug("jobExecution : " + String.valueOf(jobExecution));
		logger.debug("job Parameters :"
				+ String.valueOf(jobExecution.getJobParameters()));

		String clientSecretFileName = String.valueOf(jobExecution
				.getJobParameters().getString("client_secret"));
		logger.debug(clientSecretFileName);
		// String clientSecretFileName = "/vistaramclient_secret.json";
		try {
			vistaramGmailMessageBookingDetailProcessor
					.setJobExecution(jobExecution);
			Gmail service = GmailUtils.getGmailService(clientSecretFileName);
			jobExecution.getExecutionContext().put("service", service);
			// Print the labels in the user's account.
			// String user = "me";
			String user = String.valueOf(jobExecution.getJobParameters()
					.getString("user"));
			// String query =
			// "label:inbox-goibibo from:hotelpartners@goibibo.com after:2016/06/01";
			String q = String.valueOf(jobExecution.getJobParameters()
					.getString("query"));
			List<Message> messages = GmailUtils.listMessagesMatchingQuery(
					service, user, q);
			for (Message message : messages) {
				logger.debug(String.valueOf(message));
				
				List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
				BookingDetail bookingDetail = vistaramGmailMessageBookingDetailProcessor.process(message);
				if(null != bookingDetail) {
					bookingDetails.add(bookingDetail);
					try {
						bookingDetailWriter.write(bookingDetails);
					} catch (Exception e) {
						logger.error("Exception while saving bookingDetails : "
								+ bookingDetails, e);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(
					"Exception in VistaramGmailDataExtractorTask || execute : ",
					e);
		} finally {
			jobExecution.getExecutionContext().remove("service");
		}

		return RepeatStatus.FINISHED;
	}

	public VoucherDetail extractVoucherDetail(Message message) throws Exception {

		VoucherDetail voucherDetails = null;
		MessagePart payload = message.getPayload();
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
			if (payload2.getParts().size() > 1) {
				data = new String(Base64.decodeBase64(payload2.getParts()
						.get(1).getBody().getData()));
			} else {
				data = new String(Base64.decodeBase64(payload2.getParts()
						.get(0).getBody().getData()));
			}
			voucherDetails = VistaramMessageUtils
					.extractGoIbiboVoucherDetailsFromHtml(null, from, data);
		}

		if (from.equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>")
				&& subject.contains("Hotel Booking on MakeMyTrip.com")) {
			String data = new String(Base64.decodeBase64(payload2.getParts()
					.get(0).getBody().getData()));

			voucherDetails = VistaramMessageUtils
					.extractMakeMyTripVoucherDetailsFromHtml(null, from, data);
		}

		return voucherDetails;
	}

	private HotelDetail findHotel(String hotelIdentifier, String bookingAgent) {
		// logger.debug(hotelIdentifier+" <--> "+bookingAgent);
		for (HotelDetail hotel : hotels) {
			logger.debug(String.valueOf(hotel));
			if (hotel.getHotelIdentifierName()
					.equalsIgnoreCase(hotelIdentifier)
					&& hotel.getBookingAgent().equalsIgnoreCase(bookingAgent)) {
				return hotel;
			}
		}

		return null;
	}

}
