package com.vistaram.batch.processor;

import java.util.Arrays;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;


public class VistaramEmailMessageProcessor implements ItemProcessor<Message, VoucherDetail> {
	
	private static Logger logger = LoggerFactory.getLogger(VistaramEmailMessageProcessor.class);

	@Override
	public VoucherDetail process(Message message) throws Exception {
		logger.debug("VistaramEmailMessageProcessor || process()-->");
		VoucherDetail voucherDetails = null;
		logger.debug("To: "+Arrays.toString(message.getRecipients(RecipientType.TO)));
		logger.debug("Subject: " + message.getSubject());
		
		logger.debug("From: " + message.getFrom()[0]);
		
		logger.debug("All Recipients: "+Arrays.toString(message.getAllRecipients()));
		logger.debug("Received Date : "+message.getReceivedDate());
		
		String from = message.getFrom()[0].toString();
		String subject = message.getSubject();
		
		if (from.contains("hotelpartners@goibibo.com") && subject.contains("Confirm Hotel Booking") ) {
			voucherDetails = VistaramMessageUtils.extractGoIbiboVoucherDetailsFromMessage(message);
		}
		
		if(from.equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>")
				  && subject.contains("Hotel Booking on MakeMyTrip.com")) { 
			voucherDetails = VistaramMessageUtils.extractMakeMyTripVoucherDetailsFromMessage(message);
		}
		logger.debug("<-- VistaramEmailMessageProcessor || process()");		
		return voucherDetails;
	}

}
