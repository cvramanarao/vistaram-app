package com.vistaram.batch.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;
import org.springframework.beans.factory.InitializingBean;

public class VistaramEmailMessageReader extends
		AbstractPaginatedDataItemReader<Message> implements InitializingBean {


	public VistaramEmailMessageReader() {
		setExecutionContextName("VistaramEmailDataContext");
		setPageSize(100);
	}

	private Folder emailFolder = null;

	private Store store = null;

	private JobExecution jobExecution;

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		// close the store and folder objects
		if (null != emailFolder)
			try {
				emailFolder.close(false);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (null != store)
			try {
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		System.out.println("<-- afterStep");
	}

	@Override
	protected Iterator<Message> doPageRead() {
		System.out.println("VistaramEmailMessageReader || doPageRead()-->");
		System.out.println("Reading page : "+page);
		
		
		List<Message> messagesList = new ArrayList<Message>();
		
		String folder = String.valueOf(jobExecution.getJobParameters().getString("folder"));
		String host = "imap.gmail.com";// change accordingly
		String port = "993";
		String mailStoreType = "imaps";
		String username =String.valueOf(jobExecution.getJobParameters().getString("email")); // change accordingly
		String password = String.valueOf(jobExecution.getJobParameters().getString("password"));// change accordingly
		
		System.out.println("Folder : "+folder);
		System.out.println("email : "+username);
		System.out.println("key : "+password);
		try {

			// create properties field
			Properties properties = new Properties();

			// imap settings

			properties.put("mail.imaps.host", host);
			properties.put("mail.imaps.port", port);
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server

			store = emailSession.getStore(mailStoreType);

			store.connect(host, username, password);

			
			
			// create the folder object and open it
			// create the folder object and open it
			emailFolder = store.getFolder(folder);
			emailFolder.open(Folder.READ_ONLY);
			if(page == 0){
				setMaxItemCount(emailFolder.getMessageCount());
			}
			// retrieve the messages from the folder in an array and print it

			//Message[] messages = emailFolder.getMessages();
			int start = pageSize * page+1;
			int end = pageSize * page + pageSize;
			
			Message[] messages = emailFolder.getMessages(start, end);
			
			System.out.println("messages.length---" + messages.length);

			return Arrays.asList(messages).iterator();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			System.out
					.println("<-- VistaramEmailMessageReader || doPageRead()");

		}

		System.out.println("VistaramEmailMessageReader || doPageRead()-->");
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		
	
	}

}
