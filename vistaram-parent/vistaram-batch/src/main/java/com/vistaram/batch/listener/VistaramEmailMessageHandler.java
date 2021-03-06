package com.vistaram.batch.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.vistaram.batch.processor.VistaramEmailMessageBookingDetailProcessor;
import com.vistaram.data.relational.domain.BookingDetail;


public class VistaramEmailMessageHandler implements MessageHandler {

	@Autowired
	private VistaramEmailMessageBookingDetailProcessor vistaramEmailMessageBookingDetailProcessor;
	
	@Autowired
	private ItemWriter<BookingDetail> bookingDetailWriter;
	
	private static Logger logger = LoggerFactory.getLogger(VistaramEmailMessageHandler.class);
	
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		
		logger.debug("handleMessage() -->");
		logger.debug("message: "+message);
		/*MessageHeaders headers = message.getHeaders();
		for(Map.Entry<String, Object> entry : headers.entrySet()){
			logger.debug(entry.getKey()+" -- "+entry.getValue());
		}*/
		Object payload = message.getPayload();
		//AbstractMailReceiver
		MimeMessage mimeMessage = (MimeMessage)payload;
		try {
			String subject = mimeMessage.getSubject();
			logger.debug("subject: "+subject);
		
			Object content = mimeMessage.getContent();
			logger.debug("content: "+content);
			BookingDetail bookingDetail = vistaramEmailMessageBookingDetailProcessor.process(mimeMessage);		
			if(null != bookingDetail) {
				List<BookingDetail> bookingDetails = new ArrayList<BookingDetail>();
				bookingDetails.add(bookingDetail);
				bookingDetailWriter.write(bookingDetails);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Exception Occured : ", e);
		} catch (javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("Messageing Exception Occured : ", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception Occured : ", e);
		}
				
		logger.debug("payload : "+payload);
		logger.debug("<-- handleMessage()");
	}
}
