package com.vistaram.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;

import com.vistaram.batch.processor.VistaramEmailMessageProcessor;
import com.vistaram.batch.writer.VistaramDetailsWriter;
import com.vistaram.data.domain.VoucherDetail;


public class VistaramEmailMessageHandler implements MessageHandler {

	@Autowired
	private VistaramEmailMessageProcessor vistaramEmailMessageProcessor;
	
	
	@Autowired
	private VistaramDetailsWriter vistaramDetailsWriter;
	
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		
		System.out.println("handleMessage() -->");
		System.out.println("message: "+message);
		MessageHeaders headers = message.getHeaders();
		for(Map.Entry<String, Object> entry : headers.entrySet()){
			System.out.println(entry.getKey()+" -- "+entry.getValue());
		}
		Object payload = message.getPayload();
		//AbstractMailReceiver
		MimeMessage mimeMessage = (MimeMessage)payload;
		/*try {
			Enumeration allHeaders = mimeMessage.getAllHeaders();
			while(allHeaders.hasMoreElements()){
				System.out.println(allHeaders.nextElement());
			}
		} catch (javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String subject;
		try {
			subject = mimeMessage.getSubject();
			System.out.println("subject: "+subject);
		} catch (javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Object content = mimeMessage.getContent();
			System.out.println("content: "+content);
			VoucherDetail voucherDetails = vistaramEmailMessageProcessor.process(mimeMessage);		
			if(null != voucherDetails) {
				List<VoucherDetail> voucherDetailsList = new ArrayList<VoucherDetail>();
				voucherDetailsList.add(voucherDetails);
				vistaramDetailsWriter.write(voucherDetailsList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (javax.mail.MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		System.out.println("payload : "+payload);
		System.out.println("<-- handleMessage()");
	}

	public VistaramEmailMessageProcessor getVistaramEmailMessageProcessor() {
		return vistaramEmailMessageProcessor;
	}

	public void setVistaramEmailMessageProcessor(
			VistaramEmailMessageProcessor vistaramEmailMessageProcessor) {
		this.vistaramEmailMessageProcessor = vistaramEmailMessageProcessor;
	}

	public VistaramDetailsWriter getVistaramDetailsWriter() {
		return vistaramDetailsWriter;
	}

	public void setVistaramDetailsWriter(VistaramDetailsWriter vistaramDetailsWriter) {
		this.vistaramDetailsWriter = vistaramDetailsWriter;
	}
	
	

}
