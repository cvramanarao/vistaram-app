package com.vistaram.batch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vistaram.data.domain.MakeMyTripVoucherDetailsBuilder;
import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.domain.VoucherDetailsBuilder;

public class VistaramMessageUtils {
	
	private static Logger logger = LoggerFactory.getLogger(VistaramMessageUtils.class);
	
	public static VoucherDetail extractGoIbiboVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		MimeMultipart mimePart = (MimeMultipart) message
				.getContent();
		logger.debug("total parts in this message : "
				+ mimePart.getCount());
		for (int j = 0; j < mimePart.getCount(); j++) {

			//logger.debug("body part for : " + j);
			BodyPart bodyPart = mimePart.getBodyPart(j);
			//logger.debug(bodyPart);
			InputStream in = bodyPart.getInputStream();
			extractDetailsFromInputStream(voucherDetailsMap, tariffDetailsList,
					in);
			in.close();
		}
		
		
		
		logger.debug(String.valueOf(voucherDetailsMap));
		
		VoucherDetailsBuilder builder = new VoucherDetailsBuilder();
		
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("goibibo.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(Arrays.toString(message.getRecipients(RecipientType.TO)));
		return voucherDetails;
	}
	
	
	public static VoucherDetail extractGoIbiboVoucherDetailsFromHtml(String from, String to, String html){
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		extractVoucherDetailsFromGoIbiboHtml(voucherDetailsMap, tariffDetailsList, html);
		VoucherDetailsBuilder builder = new VoucherDetailsBuilder();
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("goibibo.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(to);
		return voucherDetails;
	}

	private static void extractDetailsFromInputStream(
			Map<String, String> voucherDetailsMap,
			List<Map<String, String>> tariffDetailsList, InputStream in)
			throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}

		extractVoucherDetailsFromGoIbiboHtml(voucherDetailsMap, tariffDetailsList, sb.toString());
	}


	private static void extractVoucherDetailsFromGoIbiboHtml(
			Map<String, String> voucherDetailsMap,
			List<Map<String, String>> tariffDetailsList, String html) {
		
		//logger.debug("html : "+html);
		
		Document document = Jsoup.parse(html);
		
		//Extracting tariff details
		Elements tariffDetailsTable = document.select("table:contains(tariff applicable:) + table");
		Elements headTds = tariffDetailsTable.select("table>tbody>tr>td>table>tbody>tr>td>table>thead>tr>td");
		List<String> keys = new ArrayList<String>();
		for(Element td: headTds) {
			keys.add(td.ownText());
		}
		
		//logger.debug("keys : "+keys);
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
		
		//logger.debug(tariffDetailsList);
		
		//Extracting Reservation Details:
		
	
		
		Elements tables = document.select("body > table");
		//logger.debug("tables size : " + tables.size());
		
		for(Element table : tables){
			//logger.debug("table----------------------------------------->");
			//logger.debug(table.html());
			
			if(table.text().contains("Booking ID")) {
				String voucherString = table.text();
				logger.debug("voucherString : "
						+ voucherString);
				voucherDetailsMap.put("voucher number", voucherString.substring(voucherString.indexOf(":")+1));
			}
			
			Elements trs = table
					.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
			for (Element tr : trs) {
				//logger.debug("children in tr "+tr.childNodeSize()+" -- "+tr.children().size());
				if(tr.children().size() > 1) {
					String key = tr.child(0).text().toLowerCase();
					String value = tr.child(1).ownText();
					voucherDetailsMap.put(key, value);
				}
				
			}
			//logger.debug("<-----------------------------------------table");
		}
		
		logger.debug("voucherDetailsMap: "+voucherDetailsMap);
	}
	
	public static VoucherDetail extractMakeMyTripVoucherDetailsFromHtml(String from, String to, String html){
		logger.debug("VistaramMessageUtils || extractMakeMyTripVoucherDetailsFromHtml");
		Map<String, String> voucherDetailsMap = new TreeMap<String, String>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareToIgnoreCase(o2);
			}
		});
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		extractVoucherDetailsFromMakeMyTripHtml(voucherDetailsMap, tariffDetailsList, html);
		VoucherDetailsBuilder builder = new MakeMyTripVoucherDetailsBuilder();
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList );
		
		//VoucherDetail voucherDetails = VoucherDetailsBuilder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("makemytrip.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(to);
		logger.debug("<-- VistaramMessageUtils || extractMakeMyTripVoucherDetailsFromHtml");
		return voucherDetails;
	}
	
	private static void extractVoucherDetailsFromMakeMyTripHtml(
			Map<String, String> voucherDetailsMap,
			List<Map<String, String>> tariffDetailsList, String html) {
		
		logger.debug("html : "+html);
		
		Document document = Jsoup.parse(html);
		
		Elements trs = document.select("body>table>tbody>tr>td>table>tbody>tr");
		if(trs.size() == 1) {
			trs = document.select("body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
		}
		
		//Elements trs = document.select("body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
		logger.debug("trs size : " + trs.size());
		
		Element voucherNumberTr = trs.get(0);
		//logger.debug("voucherNumberTr: "+voucherNumberTr);
		
		Elements voucherNumberTds = voucherNumberTr.select("tr>td");
		Elements textSpans = voucherNumberTds.get(0).select("td>span");
		
		//Elements textSpans = voucherNumberTr.select("tr>td>span");
		/*if(textSpans.size() < 0) {
			textSpans = voucherNumberTr.select("tr>td>table>tbody>tr>td>span");
		}*/
		
		//Extract voucher number
		//Elements 
		
		PaymentType paymentType = PaymentType.ONLINE;
		logger.debug("total Text Spans "+textSpans.size());
		for(int i=0;i<textSpans.size();i++){
			String text = textSpans.get(i).text();
			text = text.trim();
			//logger.debug("span text : "+text);
			if(text.contains("Booking ID")){
				String voucherNumber = text.substring(text.indexOf("-")+1).trim();
				logger.debug("Voucher : "+voucherNumber);
				voucherDetailsMap.put("VoucherNumber", voucherNumber);
			}
			
			if(text.startsWith("Booking Date") || text.startsWith("Voucher Date")){
			    String bookingDateStr = text.substring(text.indexOf("-")+1).trim();
				logger.debug("Booking Date :"+bookingDateStr);
				voucherDetailsMap.put("BookingDate", bookingDateStr);
			}

		}
		
		
		
		
		if (null == voucherDetailsMap.get("VoucherNumber")){
			 voucherNumberTr = trs.get(3);
			 //logger.debug("Second VoucherNumber Tr" + voucherNumberTr);
			 Elements children = voucherNumberTr.select("tr>td>p>strong");
			 if(children.size() > 0){
				String bookingId = children.get(1).ownText();
				bookingId = bookingId.trim();
				String voucherNumber = bookingId.substring("Booking ID".length()).trim();
				logger.debug("Voucher : "+voucherNumber);
				voucherDetailsMap.put("VoucherNumber", voucherNumber);
			 }
		}
		
		logger.debug("Voucher Number : "+voucherDetailsMap.get("VoucherNumber"));
		
		
		
		Element hotelDetailsTr = null;
		int index = -1;	
		for (int i=1;i<trs.size();i++){
			if(trs.get(i).text().equalsIgnoreCase("Hotel Details")) {
				//logger.debug("index of i for hotelDetails " +i);
				index = i;
				hotelDetailsTr = trs.get(i);
				break;
			}
		}
		
		int hotelDetailIndex = index;
		//logger.debug("hotelDetailsTr : "+hotelDetailsTr);
		//logger.debug(index+" vs "+hotelDetailsTr.siblingIndex());
		index += 2;
		Element hotelTr = trs.get(index);
		//logger.debug("hotelTr: "+hotelTr);
		String hotelAndCity = null;
		Element hotelAndCityEl = hotelTr.select("td>table>tbody>tr>td>table>tbody>tr>td").get(0);
		//logger.debug(hotelAndCityEl);
		if(hotelAndCityEl.child(0).tag().getName().equalsIgnoreCase("span")){
			hotelAndCity = hotelAndCityEl.text();
		}else {
			hotelAndCity = hotelAndCityEl.select("table>tbody>tr>td").get(0).text();
		}
		//hotelAndCity = hotelTr.select("td>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td").get(0).text();
		logger.debug("hotelAndCity: "+hotelAndCity);
		voucherDetailsMap.put("HotelAndCity", hotelAndCity);
		index +=2;
		
		for(int i = 0;i<trs.size();i++){
			
			Element row = trs.get(i);
			
			
			Element cell = row.child(0);
			if(cell.children().size() > 0){
				//logger.debug(i+") "+row);
				if(!cell.child(0).tagName().equalsIgnoreCase("table")){
					
					for(int j=0;j<row.children().size();j++) {
					
						String data = row.child(j).text();
						//logger.debug(data);
						String[] tokens = data.split(":");
						String key = tokens[0].trim();
						String value = data.substring(tokens[0].trim().length()).trim();
						if(key.length() < 30  && value.length() < 50) {
							voucherDetailsMap.put(key, value.isEmpty()?"":value.substring(1).trim());
						}
					
					}
				
				} else {
					Elements detailTrs = cell.child(0).select("table>tbody>tr");
					if(detailTrs.get(0).child(0).text().equalsIgnoreCase("Room")){
						
						List<String> keys = new ArrayList<String>();
						Elements reservationDetailsKeys = detailTrs.get(0).children();
						for(int j=0;j<reservationDetailsKeys.size();j++){
							String key = reservationDetailsKeys.get(j).text();
							keys.add(key);
						}
						
						
						Elements reservationDetailsVals = detailTrs.get(1).children();
						List<String> vals = new ArrayList<String>();
						for(int j=0;j<reservationDetailsVals.size();j++){
							vals.add(reservationDetailsVals.get(j).text());
						}
						
						int j=0;
						for(String key:keys){
							voucherDetailsMap.put(key, vals.get(j++));
						}
						
						
						for(int k=0;k<detailTrs.size();k++){
							if(k < 2)
								continue;
							String key = detailTrs.get(k).child(0).text();
							if(detailTrs.get(k).children().size() > 1) {
								String value = detailTrs.get(k).child(1).text();
								voucherDetailsMap.put(key, value);
							}
						}
					}
				}
			}
		}
		
		logger.debug(String.valueOf(voucherDetailsMap));
	}
	public static VoucherDetail extractMakeMyTripVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		MimeMultipart mimePart = (MimeMultipart) message
				.getContent();
		BodyPart emaiLBodyPart = mimePart.getBodyPart(0);
		logger.debug(String.valueOf(emaiLBodyPart));
		InputStream in = emaiLBodyPart.getInputStream();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(in));
		StringBuffer sb = new StringBuffer();
		String line = null;
		while (null != (line = br.readLine())) {
			sb.append(line);
		}
		String html = sb.toString();
		logger.debug("Make My Trip : "+html);
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		extractVoucherDetailsFromMakeMyTripHtml(voucherDetailsMap, tariffDetailsList, html);
		//logger.debug(voucherDetailsMap);
		VoucherDetailsBuilder builder = new MakeMyTripVoucherDetailsBuilder();
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList );
		voucherDetails.setBookingAgent("makemytrip.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(Arrays.toString(message.getRecipients(RecipientType.TO)));
		return voucherDetails;
	}
}
