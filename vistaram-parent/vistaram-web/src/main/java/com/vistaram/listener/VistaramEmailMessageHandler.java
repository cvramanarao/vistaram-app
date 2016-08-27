package com.vistaram.listener;

import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.vistaram.batch.processor.VistaramEmailMessageProcessor;
import com.vistaram.batch.writer.VistaramDetailsWriter;
import com.vistaram.data.domain.VoucherDetail;


@Component
public class VistaramEmailMessageHandler implements MessageHandler {

	@Autowired
	private VistaramEmailMessageProcessor vistaramEmailMessageProcessor;
	
	@Autowired
	private VistaramDetailsWriter vistaramDetailsWriter;
	
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
			VoucherDetail voucherDetail = vistaramEmailMessageProcessor.process(mimeMessage);		
			if(null != voucherDetail) {
				vistaramDetailsWriter.write(voucherDetail);
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
