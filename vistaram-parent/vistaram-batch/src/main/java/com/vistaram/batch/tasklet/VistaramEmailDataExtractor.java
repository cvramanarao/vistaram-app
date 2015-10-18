package com.vistaram.batch.tasklet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

	@Override
	public RepeatStatus execute(StepContribution stepContribution,
			ChunkContext chunkContext) throws Exception {

		/*
		 * String host = "imap.gmail.com";// change accordingly String port =
		 * "993"; String mailStoreType = "imaps"; String username =
		 * "vistaramrooms@gmail.com";// change accordingly String password =
		 * "vistaram@66669";// change accordingly
		 */

		String host = "pop.gmail.com";// change accordingly
		String mailStoreType = "pop3s";
		String username = "vistaramrooms@gmail.com";// change accordingly
		String password = "vistaram@66669";// change accordingly */
		String port = "995";
		/*
		 * String host = "vistaram.com";// change accordingly String
		 * mailStoreType = "pop3"; String username = "info@vistaram.com";//
		 * change accordingly String password = "^z]teJspdI6)";// change
		 * accordingly
		 */

		check(host, port, mailStoreType, username, password);

		return RepeatStatus.FINISHED;
	}

	public void check(String host, String port, String storeType, String user,
			String password) {
		Map<String, Map<String, String>> voucherDetails = new HashMap<String, Map<String, String>>();
		try {

			// create properties field
			Properties properties = new Properties();
			// pop3 settings
			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", port);
			properties.put("mail.pop3.starttls.enable", "true");

			// imap settings
			/*
			 * properties.put("mail.imaps.host", host);
			 * properties.put("mail.imaps.port", port);
			 * properties.put("mail.imaps.starttls.enable", "true");
			 */
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
			emailFolder.open(Folder.READ_ONLY);

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
				
				/*if (message.getFrom()[0].toString().equalsIgnoreCase("hotelpartners@goibibo.com") &&  message.getSubject().contains("Confirm Hotel Booking") ) {
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
					voucherDetails.put(voucher.trim(),
					extractGoIbiboVoucherDetailsFromMessage(message));

					System.out.println("---------------------------------");
					
				}*/
				
				
				
				
				if(message.getFrom()[0].toString().equalsIgnoreCase("MakeMyTrip <noreply@makemytrip.com>") && message.getSubject().contains("Hotel Booking on MakeMyTrip.com")) {
					++vouchers;
					System.out.println("Text: "
							+ message.getContent().toString());
					
					extractMakeMyTripVoucherDetails(message);
				}
				
				if (vouchers >= 3)
					break;
				
				System.out.println("---------------------------------");

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

		System.out.println(voucherDetails);
	}

	private Map<String, String> extractGoIbiboVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetails = new HashMap<String, String>();
		MimeMultipart mimePart = (MimeMultipart) message
				.getContent();
		System.out.println("total parts in this message : "
				+ mimePart.getCount());
		for (int j = 0; j < mimePart.getCount(); j++) {

			System.out.println("body part for : " + j);
			BodyPart bodyPart = mimePart.getBodyPart(j);
			System.out.println(bodyPart);
			InputStream in = bodyPart.getInputStream();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(in));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while (null != (line = br.readLine())) {
				sb.append(line);
			}

			Document document = Jsoup.parse(sb.toString());
			Elements tables = document.select("body > table");
			System.out.println("tables size : " + tables.size());
			
			if (tables.size() > 1) {
				Element voucherNumberTable = tables.get(0);

				String voucherString = voucherNumberTable.text();
				System.out.println("voucherString : "
						+ voucherString);
				// voucherDetails.put("voucher", voucherString);
				Element reservationDetailsTable = tables.get(2);
				Elements trs = reservationDetailsTable
						.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
				for (Element tr : trs) {
					String key = tr.child(0).ownText();
					String value = tr.child(1).ownText();
					voucherDetails.put(key, value);
				}
				
			}
		}
		
		return voucherDetails;
	}
	
	private Map<String, String> extractMakeMyTripVoucherDetails(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetails = new HashMap<String, String>();
		MimeMultipart mimePart = (MimeMultipart) message
				.getContent();
		BodyPart emaiLBodyPart = mimePart.getBodyPart(0);
		System.out.println(emaiLBodyPart);
		InputStream in = emaiLBodyPart.getInputStream();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}

		Document document = Jsoup.parse(sb.toString());
		Elements trs = document.select("body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
		System.out.println("trs size : " + trs.size());
		
		Element voucherNumberTr = trs.get(0);
		System.out.println(voucherNumberTr);
		Elements textSpans = voucherNumberTr.select("tr>td>table>tbody>tr>td>span");
		
		for(int i=0;i<textSpans.size();i++){
			String text = textSpans.get(i).text();
			text = text.trim();
			if(text.contains("Booking ID")){
				String voucherNumber = text.substring(text.indexOf("-")).trim();
				System.out.println("Voucher : "+voucherNumber);
			}
			
			if(text.startsWith("Booking Date")){
			    String bookingDateStr = text.substring(text.indexOf("-")).trim();
				System.out.println("Booking Date :"+bookingDateStr);
				
			}

		}
		
		
		
		return voucherDetails;
	}

}
