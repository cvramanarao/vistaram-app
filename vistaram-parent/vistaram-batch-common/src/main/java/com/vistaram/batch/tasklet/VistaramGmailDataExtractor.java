package com.vistaram.batch.tasklet;

import java.io.IOException;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.vistaram.batch.utils.GmailUtils;

public class VistaramGmailDataExtractor implements Tasklet {
	
	private JobExecution jobExecution;
	

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}
	

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
	
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
			List<Message> messages = GmailUtils.listMessagesMatchingQuery(service, user, query);
			for(Message message:messages){
				System.out.println(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RepeatStatus.FINISHED;
	}

}
