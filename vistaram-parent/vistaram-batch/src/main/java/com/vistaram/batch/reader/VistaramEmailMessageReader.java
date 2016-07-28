package com.vistaram.batch.reader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;

import com.vistaram.batch.utils.VistaramMessageUtils;

public class VistaramEmailMessageReader extends AbstractPaginatedDataItemReader<Message>{
	
	String host = "imap.gmail.com";// change accordingly 
	String port ="993"; 
	String mailStoreType = "imaps"; 
	String username =
	 "vistaramrooms@gmail.com";// change accordingly 
	String password =
	 "vistaram66669";// change accordingly
	

	@Override
	protected Iterator<Message> doPageRead() {
		System.out.println("VistaramEmailMessageReader || doPageRead()-->");
		List<Message> messagesList = new ArrayList<Message>();
		Store store = null;
		Folder emailFolder = null;
		try {

			// create properties field
			Properties properties = new Properties();
			// pop3 settings
//			properties.put("mail.pop3.host", host);
//			properties.put("mail.pop3.port", port);
//			properties.put("mail.pop3.starttls.enable", "true");

			// imap settings
			
			properties.put("mail.imaps.host", host);
			properties.put("mail.imaps.port", port);
			properties.put("mail.imaps.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			/*
			 * Session emailSession = Session.getInstance(properties, new
			 * javax.mail.Authenticator() { protected PasswordAuthentication
			 * getPasswordAuthentication() { return new
			 * PasswordAuthentication(user, password); } });
			 */

			// create the POP3 store object and connect with the pop server

			store = emailSession.getStore(mailStoreType);

			store.connect(host, username, password);

			// create the folder object and open it
			emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.HOLDS_MESSAGES);

			
			// retrieve the messages from the folder in an array and print it
			
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);
			
			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
			
				System.out.println("parsing email "+i);
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				
				System.out.println("From: " + Arrays.toString(message.getFrom()));
	
				System.out.println("Date : "+message.getReceivedDate());
				
				
				Enumeration<Header> headers = message.getAllHeaders();
				while(headers.hasMoreElements()){
					Header header = headers.nextElement();
					if(header.getName().equalsIgnoreCase("Date")){
						String dateStr = header.getValue();
						System.out.println("Date Str : "+dateStr);
						SimpleDateFormat dateFormat = new SimpleDateFormat();
						Date date = null;
						try {
							dateFormat.applyPattern("d MMM yyyy HH:mm:ss Z");
							date = dateFormat.parse(dateStr);
						} catch (Exception e) {
							
						}
						
						if(null == date){
							try {
								dateFormat.applyPattern("E, d MMM yyyy HH:mm:ss Z");
								date = dateFormat.parse(dateStr);
							} catch (Exception e) {
								
							}
						}
						System.out.println("Header Date : "+date);
					}
				}
			}

			return Arrays.asList(messages).iterator();
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close the store and folder objects
			if(null != emailFolder)
			try {
				emailFolder.close(false);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(null != store)
			try {
				store.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		System.out.println("VistaramEmailMessageReader || doPageRead()-->");
		return null;
	}


}
