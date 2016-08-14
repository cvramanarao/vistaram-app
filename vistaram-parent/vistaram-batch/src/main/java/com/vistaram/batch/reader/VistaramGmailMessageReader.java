package com.vistaram.batch.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {

	}

	@Override
	protected Iterator<Message> doPageRead() {
		System.out.println("doPageRead()-->");
		System.out.println("Reading page : "+page);
		
		setMaxItemCount(jobExecution.getJobParameters().getLong("max_items", 10).intValue());
		List<Message> messages = new ArrayList<Message>();
//		String clientSecretFileName =  String.valueOf(jobExecution.getJobParameters().getString("client_secret"));
//		 
//		//String clientSecretFileName = "/vistaramclient_secret.json";
//		try {
//			Gmail service = GmailUtils.getGmailService(clientSecretFileName);
//			
//			jobExecution.getExecutionContext().put("service", service);
//
//			// Print the labels in the user's account.
//			//String user = "me";
//			String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
//			//String query = "label:inbox-goibibo from:hotelpartners@goibibo.com after:2016/06/01";
//			String query = String.valueOf(jobExecution.getJobParameters().getString("query"));
//			//messages = GmailUtils.listMessagesMatchingQuery(service, user, query);
//			 ListMessagesResponse response = service.users().messages().list(user).setQ(query).execute();
//			 setMaxItemCount(response.getResultSizeEstimate());
//			 if(null != response.getMessages()) {
//				 messages.addAll(response.getMessages());
//			 }
//			 
//			 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ListMessagesResponse response = (ListMessagesResponse) jobExecution.getExecutionContext().get("response");
		//setMaxItemCount(response.getResultSizeEstimate().intValue());
		
		//System.out.println("getMaxItemCount: "+response.getResultSizeEstimate().intValue());
	
		try {
			String user = String.valueOf(jobExecution.getJobParameters().getString("user"));
			String query = String.valueOf(jobExecution.getJobParameters().getString("query"));
			Gmail service = (Gmail) jobExecution.getExecutionContext().get("service");
			
			
			
			if(null != response.getMessages()) {
				 messages.addAll(response.getMessages());
				 if (response.getNextPageToken() != null) {
			          String pageToken = response.getNextPageToken();
			          response = service.users().messages().list(user).setQ(query)
			              .setPageToken(pageToken).execute();
			          setMaxItemCount(response.getResultSizeEstimate().intValue());
			          jobExecution.getExecutionContext().put("response", response);
			         
			        } 
			 } else {
				 jobExecution.setExitStatus(ExitStatus.COMPLETED);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		
		System.out.println("Total messages : " + messages.size());
		
		System.out.println("<-- doPageRead() ");
		return messages.iterator();
	}

}
