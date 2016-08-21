package com.vistaram.batch.processor;



import java.util.List;

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
import com.vistaram.batch.utils.GmailUtils;
import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;

public class VistaramGmailMessageProcessor implements ItemProcessor<Message, VoucherDetail> {
	
	
	private JobExecution jobExecution;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		stepExecution.getJobExecution().getExecutionContext().remove("service");
		stepExecution.getJobExecution().getExecutionContext().remove("user");
	}

	@Override
	public VoucherDetail process(Message message) throws Exception {
		System.out.println("VistaramGmailMessageProcessor || process() -->");
		VoucherDetail voucherDetails = null;
		MessagePart payload = message.getPayload();
		
		//System.out.println("Payload : "+ payload);
		//System.out.println("Raw : "+ message.getRaw());
		//System.out.println(message.toPrettyString());
		Gmail service = (Gmail) jobExecution.getExecutionContext().get("service");
		String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
		
		
		
		Message msg = service.users().messages().get(user, message.getId()).execute();
		
		MessagePart payload2 = msg.getPayload();
		
		List<MessagePartHeader> headers = payload2.getHeaders();
		
		String from = "";
		String subject = "";
		for(MessagePartHeader header : headers){
			
			if(header.getName().equalsIgnoreCase("from")){
				from = header.getValue();
			}
			
			if(header.getName().equalsIgnoreCase("subject")){
				subject = header.getValue();
			}
		}
		System.out.println("from : "+from+" subject: "+subject);
		
		if (from.contains("hotelpartners@goibibo.com") &&  subject.contains("Confirm Hotel Booking") ) {
			
			String data = new String(Base64.decodeBase64(payload2.getParts().get(1).getBody().getData()));
			voucherDetails = VistaramMessageUtils.extractGoIbiboVoucherDetailsFromHtml(null, from, data);
		}
		
//		List<MessagePart> parts = payload2.getParts();
//	
//		for(MessagePart part : parts){
//			
//			System.out.println(part.getMimeType());
//			byte[] bodyBytes = Base64.decodeBase64(part.getBody().getData());
//			String data = new String(bodyBytes);
//			System.out.println(data);
//			
//		}
		//System.out.println("Content : \n"+GmailUtils.getContent(service, user, message));
		
		System.out.println("<-- VistaramEmailMessageProcessor || process()");		
		return voucherDetails;
	}

}
