package com.vistaram.batch.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.vistaram.batch.utils.GmailUtils;

public class VistaramGmailMessageReader extends AbstractPaginatedDataItemReader<Message> implements InitializingBean {

	private JobExecution jobExecution;

	
	
	public VistaramGmailMessageReader() {
		setExecutionContextName("VistaramGmailDataContext");
		setPageSize(100);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {

	}

	@Override
	protected Iterator<Message> doPageRead() {
		List<Message> messages = new ArrayList<Message>();
		String clientSecretFileName =  String.valueOf(jobExecution.getJobParameters().getString("client_secret"));
		//String clientSecretFileName = "/vistaramclient_secret.json";
		try {
			Gmail service = GmailUtils.getGmailService(clientSecretFileName);

			// Print the labels in the user's account.
			//String user = "me";
			String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
			//String query = "label:inbox-goibibo from:hotelpartners@goibibo.com after:2016/06/01";
			String query = String.valueOf(jobExecution.getJobParameters().getString("query"));
			messages = GmailUtils.listMessagesMatchingQuery(service, user,
					query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total messages : " + messages.size());
		return messages.iterator();
	}

}
