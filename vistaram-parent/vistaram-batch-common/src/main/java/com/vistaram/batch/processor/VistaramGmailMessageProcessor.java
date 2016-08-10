package com.vistaram.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.vistaram.data.domain.VoucherDetail;

public class VistaramGmailMessageProcessor implements ItemProcessor<Message, VoucherDetail> 

{

	@Override
	public VoucherDetail process(Message message) throws Exception {
		System.out.println("VistaramGmailMessageProcessor || process() -->");
		VoucherDetail voucherDetails = new VoucherDetail();
		System.out.println(message.getPayload());
		System.out.println(message);
		MessagePart payload = message.getPayload();
		System.out.println(payload.getBody().getData());
		
		System.out.println("<-- VistaramEmailMessageProcessor || process()");		
		return voucherDetails;
	}

}
