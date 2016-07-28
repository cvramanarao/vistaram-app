package com.vistaram.batch.processor;

import javax.mail.Message;

import org.springframework.batch.item.ItemProcessor;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetails;

public class VistaramEmailMessageProcessor implements ItemProcessor<Message, VoucherDetails> {

	@Override
	public VoucherDetails process(Message message) throws Exception {
		System.out.println("VistaramEmailMessageProcessor || process()-->");
		VoucherDetails voucherDetails = null;
		
		System.out.println("Subject: " + message.getSubject());
		
		System.out.println("From: " + message.getFrom()[0]);
		System.out.println("Text: "
				+ message.getContent().toString());

		String voucher = message.getSubject()
				.substring(message.getSubject().lastIndexOf(" "))
				.trim();
		
		if (message.getFrom()[0].toString().equalsIgnoreCase("hotelpartners@goibibo.com") &&  message.getSubject().contains("Confirm Hotel Booking") ) {
			voucherDetails = VistaramMessageUtils.extractGoIbiboVoucherDetailsFromMessage(message);
		}
		System.out.println("<-- VistaramEmailMessageProcessor || process()");		
		return voucherDetails;
	}

}
