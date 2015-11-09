package com.vistaram.batch.tasklet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.batch.writer.VistaramDetailsWriter;
import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.TariffDetails;
import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.domain.VoucherDetailsBuilder;
import com.vistaram.data.service.VoucherDetailsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;

public class VistaramEmailDataExtractor implements Tasklet {
	
	@Autowired
	private VistaramDetailsWriter writer;

	@Override
	public RepeatStatus execute(StepContribution stepContribution,
			ChunkContext chunkContext) throws Exception {

		String host = "imap.gmail.com";// change accordingly 
		String port ="993"; 
		String mailStoreType = "imaps"; 
		String username =
		 "vistaramrooms@gmail.com";// change accordingly 
		String password =
		 "vistaram@66669";// change accordingly

		Map<String, VoucherDetails> voucherDetailsMap = extractVoucherDetails(host, port, mailStoreType, username, password);
		List<VoucherDetails> voucherDetailsList = new ArrayList<VoucherDetails>();
		voucherDetailsList.addAll(voucherDetailsMap.values());
		writer.write(voucherDetailsList);

		return RepeatStatus.FINISHED;
	}

	public Map<String, VoucherDetails> extractVoucherDetails(String host, String port, String storeType, String user,
			String password) {
		Map<String, VoucherDetails> voucherDetailsMap = new HashMap<String,VoucherDetails>();
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

			Store store = emailSession.getStore(storeType);

			store.connect(host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.HOLDS_MESSAGES);

			
			// retrieve the messages from the folder in an array and print it
			
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			int vouchers = 0;
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
				
				if (message.getFrom()[0].toString().equalsIgnoreCase("hotelpartners@goibibo.com") &&  message.getSubject().contains("Confirm Hotel Booking") ) {
					System.out.println("---------------------------------");
					++vouchers;
					System.out.println("Email Number " + (i + 1));
					System.out.println("Subject: " + message.getSubject());
					
					System.out.println("From: " + message.getFrom()[0]);
					System.out.println("Text: "
							+ message.getContent().toString());

					String voucher = message.getSubject()
							.substring(message.getSubject().lastIndexOf(" "))
							.trim();
					
					
					voucherDetailsMap.put(voucher.trim(),
							VistaramMessageUtils.extractGoIbiboVoucherDetailsFromMessage(message));
					
					
					System.out.println("---------------------------------");
					
				}
		
				/*if(message.getFrom()[0].toString().equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>") && message.getSubject().contains("Hotel Booking on MakeMyTrip.com")) {
					++vouchers;
					System.out.println("Text: "
							+ message.getContent().toString());
					
					extractMakeMyTripVoucherDetails(message);
				}*/
				
				
				System.out.println("---------------------------------");
				
				if(vouchers > 3)
					break;

			}

			System.out.println("No. of vouchers existing : " + vouchers);
			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(voucherDetailsMap);
		return voucherDetailsMap;
	}

}
