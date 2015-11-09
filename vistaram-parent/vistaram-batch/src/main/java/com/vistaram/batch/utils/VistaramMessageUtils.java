package com.vistaram.batch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.domain.VoucherDetailsBuilder;

public class VistaramMessageUtils {
	
	public static VoucherDetails extractGoIbiboVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
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
				voucherDetailsMap.put("voucherNumber", voucherString.substring(voucherString.indexOf(":")+1));
				Element reservationDetailsTable = tables.get(2);
				Elements trs = reservationDetailsTable
						.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
				for (Element tr : trs) {
					String key = tr.child(0).ownText();
					String value = tr.child(1).ownText();
					voucherDetailsMap.put(key, value);
				}
				
				Element tariffDetailsTable = tables.get(6);
				Elements headTds = tariffDetailsTable.select("table>tbody>tr>td>table>tbody>tr>td>table>thead>tr>td");
				List<String> keys = new ArrayList<String>();
				for(Element td: headTds) {
					keys.add(td.ownText());
				}
				
				System.out.println("keys : "+keys);
				Elements bodyTrs = tariffDetailsTable.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
				
				
				
				for(Element tr: bodyTrs) {
					Map<String, String> tariffDetailRecord = new HashMap<String, String>();
					Elements tds = tr.children();
					int i=0;
					for(Element td : tds){
						tariffDetailRecord.put(keys.get(i++), td.ownText());
					}
					tariffDetailsList.add(tariffDetailRecord);
				}
				
				
				Element summaryTable = tables.get(7);
				Elements summaryRows = summaryTable.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
				for(Element tr : summaryRows) {
					
					
					
					String key = tr.child(0).text();
					String value = tr.child(1).ownText();
					System.out.println(key+" -- "+value);
					voucherDetailsMap.put(key, value);
				}
			}
		}
		
		System.out.println(voucherDetailsMap);
		VoucherDetails voucherDetails = VoucherDetailsBuilder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("goibibio.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		return voucherDetails;
	}
	
	public static Map<String, String> extractMakeMyTripVoucherDetails(Message message) throws IOException, MessagingException {
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
