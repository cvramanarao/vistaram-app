package com.vistaram.batch.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.vistaram.batch.utils.GmailUtils;

public class VistaramGmailMessageReader extends AbstractPaginatedDataItemReader<Message> implements InitializingBean {

	private JobExecution jobExecution;

	private static Logger logger = LoggerFactory.getLogger(VistaramGmailMessageReader.class);
	
	public VistaramGmailMessageReader() {
		setExecutionContextName("VistaramGmailDataContext");
		//setPageSize(100);
	
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		logger.debug("VistaramGmailMessageReader || beforeStep()-->");
		jobExecution = stepExecution.getJobExecution();
		String clientSecretFileName =  String.valueOf(jobExecution.getJobParameters().getString("client_secret"));
		 
		//String clientSecretFileName = "/vistaramclient_secret.json";
		try {
			Gmail service = GmailUtils.getGmailService(clientSecretFileName);
			
			jobExecution.getExecutionContext().put("service", service);

			// Print the labels in the user's account.
			//String user = "me";
			String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
			//String query = "label:inbox-goibibo from:hotelpartners@goibibo.com after:2016/06/01";
			String query = String.valueOf(jobExecution.getJobParameters().getString("query"));
			//messages = GmailUtils.listMessagesMatchingQuery(service, user, query);
			 ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
			 jobExecution.getExecutionContext().put("response", response);
		} catch (Exception e) {
			logger.error("Exception Occured in before Step : ", e);
		}
		logger.debug("<-- VistaramGmailMessageReader || beforeStep()");
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {

	}

	@Override
	protected Iterator<Message> doPageRead() {
		logger.debug("doPageRead()-->");
		logger.debug("Reading page : "+page);
		String maxCount = jobExecution.getJobParameters().getString("max_items", "10").trim();
		logger.debug("maxCount : "+maxCount);
		setMaxItemCount(Integer.valueOf(maxCount));
		List<Message> messages = new ArrayList<Message>();
		try {
			ListMessagesResponse response = (ListMessagesResponse) jobExecution.getExecutionContext().get("response");
			String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
			String query = String.valueOf(jobExecution.getJobParameters().getString("query"));
			Gmail service = (Gmail) jobExecution.getExecutionContext().get("service");
			messages = GmailUtils.listMessagesMatchingQuery(service, user, query);
		} catch (Exception e) {
			logger.error("Exception Occured : ", e);
		}
		logger.debug("Total messages : " + messages.size());
		
		logger.debug("<-- doPageRead() ");
		return messages.iterator();
	}

}
