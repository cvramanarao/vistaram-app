package com.vistaram.batch.reader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger logger = LoggerFactory.getLogger(VistaramEmailMessageReader.class);

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		System.out.println("VistaramEmailMessageReader ||  afterStep");
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

		System.out.println("<-- VistaramEmailMessageReader || afterStep");
	}

	@Override
	protected Iterator<Message> doPageRead() {
		System.out.println("VistaramEmailMessageReader || doPageRead()-->");
		System.out.println("Reading page : " + page);

		List<Message> messagesList = new ArrayList<Message>();

		String folder = String.valueOf(jobExecution.getJobParameters()
				.getString("folder"));
		String host = "imap.gmail.com";// change accordingly
		String port = "993";
		String mailStoreType = "imaps";
		String username = String.valueOf(jobExecution.getJobParameters()
				.getString("email")); // change accordingly
		String password = String.valueOf(jobExecution.getJobParameters()
				.getString("password"));// change accordingly

		System.out.println("Folder : " + folder);
		System.out.println("email : " + username);
		System.out.println("key : " + password);
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

			// creates a search criterion
			SearchTerm searchCondition = new SearchTerm() {
				@Override
				public boolean match(Message message) {

					String after = jobExecution
							.getJobParameters().getString("after");
					String before = jobExecution
							.getJobParameters().getString("before");
					String subject = jobExecution
							.getJobParameters().getString("subject");
					String from = jobExecution
							.getJobParameters().getString("from");
					
					System.out.println("after : "+after+", before: "+before+", subject: "+subject+", from: "+from);
					if(null != subject){
						System.out.println("only subject: "+subject);
					}
					if(null != from){
						System.out.println("only from: "+from);
					}
					Date afterDate = null;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
					try {
						afterDate = sdf.parse(after);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Date beforeDate = null;
					try {
						beforeDate = sdf.parse(before);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (null == afterDate) {
						Calendar cl = Calendar.getInstance(TimeZone
								.getTimeZone("IST"));
						cl.add(Calendar.DATE, -1);
						cl.add(Calendar.HOUR_OF_DAY, 0);
						cl.add(Calendar.MINUTE, 0);
						cl.add(Calendar.SECOND, 0);
						cl.add(Calendar.MILLISECOND, 0);
						afterDate = cl.getTime();
					}
					
					if (null == beforeDate) {
						Calendar cl = Calendar.getInstance(TimeZone
								.getTimeZone("IST"));
						//cl.add(Calendar.DATE, -1);
						cl.add(Calendar.HOUR_OF_DAY, 23);
						cl.add(Calendar.MINUTE, 59);
						cl.add(Calendar.SECOND, 59);
						cl.add(Calendar.MILLISECOND, 999);
						beforeDate = cl.getTime();
					}

					try {

						if (null != afterDate && null != beforeDate
								&& message.getSentDate().after(afterDate)
								&& message.getSentDate().before(beforeDate)) {
							return true;
						}

						if (null != afterDate
								&& message.getSentDate().after(afterDate)) {
							return true;
						}

						if (null != beforeDate
								&& message.getSentDate().before(beforeDate)) {
							return true;
						}

					} catch (MessagingException ex) {
						ex.printStackTrace();
					}
					return false;
				}
			};

			// performs search through the folder

			//Message[] messages = emailFolder.search(searchCondition);

			if (page == 0) {
				setMaxItemCount(emailFolder.getMessageCount());
			}
			// retrieve the messages from the folder in an array and print it

			// Message[] messages = emailFolder.getMessages();
			int start = pageSize * page + 1;
			int end = pageSize * page + pageSize;
			Message[] messages = emailFolder.search(searchCondition);
			//Message[] messages = emailFolder.getMessages(start, end);

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
