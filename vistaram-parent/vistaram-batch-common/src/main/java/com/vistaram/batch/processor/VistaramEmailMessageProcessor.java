package com.vistaram.batch.processor;

import java.util.Arrays;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;

@Component
public class VistaramEmailMessageProcessor implements ItemProcessor<Message, VoucherDetail> {
	
	

	@Override
	public VoucherDetail process(Message message) throws Exception {
		System.out.println("VistaramEmailMessageProcessor || process()-->");
		VoucherDetail voucherDetails = null;
		System.out.println("To: "+Arrays.toString(message.getRecipients(RecipientType.TO)));
		System.out.println("Subject: " + message.getSubject());
		
		System.out.println("From: " + message.getFrom()[0]);
		
		System.out.println("All Recipients: "+Arrays.toString(message.getAllRecipients()));
		
		String from = message.getFrom()[0].toString();
		String subject = message.getSubject();
		
		if (from.contains("hotelpartners@goibibo.com") && subject.contains("Confirm Hotel Booking") ) {
			voucherDetails = VistaramMessageUtils.extractGoIbiboVoucherDetailsFromMessage(message);
		}
		
		if(from.equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>")
				  && subject.contains("Hotel Booking on MakeMyTrip.com")) { 
			voucherDetails = VistaramMessageUtils.extractMakeMyTripVoucherDetailsFromMessage(message);
		}
		System.out.println("<-- VistaramEmailMessageProcessor || process()");		
		return voucherDetails;
	}

}
