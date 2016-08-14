package com.vistaram.batch.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.vistaram.data.domain.MakeMyTripVoucherDetailsBuilder;
import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.domain.VoucherDetailsBuilder;

public class VistaramMessageUtils {
	
	public static VoucherDetail extractGoIbiboVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		MimeMultipart mimePart = (MimeMultipart) message
				.getContent();
		System.out.println("total parts in this message : "
				+ mimePart.getCount());
		for (int j = 0; j < mimePart.getCount(); j++) {

			//System.out.println("body part for : " + j);
			BodyPart bodyPart = mimePart.getBodyPart(j);
			//System.out.println(bodyPart);
			InputStream in = bodyPart.getInputStream();
			extractDetailsFromInputStream(voucherDetailsMap, tariffDetailsList,
					in);
			in.close();
		}
		
		
		
		//System.out.println(voucherDetailsMap);
		
		VoucherDetailsBuilder builder = new VoucherDetailsBuilder();
		
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("goibibio.com");
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
		voucherDetails.setBookingAgent("goibibio.com");
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
		
		//System.out.println("html : "+html);
		
		Document document = Jsoup.parse(html);
		
		//Extracting tariff details
		Elements tariffDetailsTable = document.select("table:contains(tariff applicable:) + table");
		Elements headTds = tariffDetailsTable.select("table>tbody>tr>td>table>tbody>tr>td>table>thead>tr>td");
		List<String> keys = new ArrayList<String>();
		for(Element td: headTds) {
			keys.add(td.ownText());
		}
		
		//System.out.println("keys : "+keys);
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
		
		//System.out.println(tariffDetailsList);
		
		//Extracting Reservation Details:
		
	
		
		Elements tables = document.select("body > table");
		//System.out.println("tables size : " + tables.size());
		
		for(Element table : tables){
			//System.out.println("table----------------------------------------->");
			//System.out.println(table.html());
			
			if(table.text().contains("Booking ID")) {
				String voucherString = table.text();
				System.out.println("voucherString : "
						+ voucherString);
				voucherDetailsMap.put("voucher number", voucherString.substring(voucherString.indexOf(":")+1));
			}
			
			Elements trs = table
					.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
			for (Element tr : trs) {
				//System.out.println("children in tr "+tr.childNodeSize()+" -- "+tr.children().size());
				if(tr.children().size() > 1) {
					String key = tr.child(0).text().toLowerCase();
					String value = tr.child(1).ownText();
					voucherDetailsMap.put(key, value);
				}
				
			}
			//System.out.println("<-----------------------------------------table");
		}
		
		System.out.println("voucherDetailsMap: "+voucherDetailsMap);
	}
	
	public static VoucherDetail extractMakeMyTripVoucherDetailsFromHtml(String from, String to, String html){
		System.out.println("VistaramMessageUtils || extractMakeMyTripVoucherDetailsFromHtml");
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		extractVoucherDetailsFromMakeMyTripHtml(voucherDetailsMap, tariffDetailsList, html);
		VoucherDetailsBuilder builder = new MakeMyTripVoucherDetailsBuilder();
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList );
		
		//VoucherDetail voucherDetails = VoucherDetailsBuilder.build(voucherDetailsMap, tariffDetailsList);
		voucherDetails.setBookingAgent("makemytrip.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(to);
		System.out.println("<-- VistaramMessageUtils || extractMakeMyTripVoucherDetailsFromHtml");
		return voucherDetails;
	}
	
	private static void extractVoucherDetailsFromMakeMyTripHtml(
			Map<String, String> voucherDetailsMap,
			List<Map<String, String>> tariffDetailsList, String html) {
		
		System.out.println("html : "+html);
		
		Document document = Jsoup.parse(html);
		
		Elements trs = document.select("body>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr");
		System.out.println("trs size : " + trs.size());
		
		
		Element voucherNumberTr = trs.get(0);
		System.out.println(voucherNumberTr);
		//Extract voucher number
		Elements textSpans = voucherNumberTr.select("tr>td>table>tbody>tr>td>span");
		for(int i=0;i<textSpans.size();i++){
			String text = textSpans.get(i).text();
			text = text.trim();
			if(text.contains("Booking ID")){
				String voucherNumber = text.substring(text.indexOf("-")+1).trim();
				System.out.println("Voucher : "+voucherNumber);
				voucherDetailsMap.put("VoucherNumber", voucherNumber);
			}
			
			if(text.startsWith("Booking Date")){
			    String bookingDateStr = text.substring(text.indexOf("-")+1).trim();
				System.out.println("Booking Date :"+bookingDateStr);
				voucherDetailsMap.put("BookingDate", bookingDateStr);
			}

		}
		
		Element hotelDetailsTr = trs.first();
				
		for (int i=1;i<trs.size();i++){
			if(trs.get(i).text().equalsIgnoreCase("Hotel Details")) {
				hotelDetailsTr = trs.get(i);
			}
		}
		
		System.out.println("hotelDetailsTr : "+hotelDetailsTr);
		int index = hotelDetailsTr.siblingIndex()+2;
		Element hotelTr = trs.get(index);
		System.out.println(hotelTr);
		String hotelAndCity = hotelTr.select("td>table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td").get(0).text();
		System.out.println("hotelAndCity: "+hotelAndCity);
		voucherDetailsMap.put("HotelAndCity", hotelAndCity);
		index +=2;
		String guestName = trs.get(index).child(0).text();
		System.out.println("guestName " +guestName.substring(guestName.indexOf(":")+1));
		voucherDetailsMap.put("GuestName", guestName.substring(guestName.indexOf(":")+1));
		index+=1;
		String guestEmail = trs.get(index).child(0).text();
		System.out.println("guestEmail " +guestEmail);
		voucherDetailsMap.put("GuestEmail", guestEmail.substring(guestEmail.indexOf(":")+1));
		index+=1;
		String guestContact = trs.get(index).child(0).text();
		System.out.println("guestContact " +guestContact);
		voucherDetailsMap.put("GuestContact", guestContact.substring(guestContact.indexOf(":")+1));
		index+=1;
		String roomType = trs.get(index).child(0).text();
		System.out.println("roomType " +roomType);
		voucherDetailsMap.put("RoomType", roomType.substring(roomType.indexOf(":")+1));
		index+=1;
		String mealPlan = trs.get(index).child(0).text();
		System.out.println("mealPlan " +mealPlan);
		voucherDetailsMap.put("mealPlan", mealPlan.substring(mealPlan.indexOf(":")+1));
		index+=2;
		Element reservationDetailsTr = trs.get(index);
		System.out.println("reservationDetailsTr : "+reservationDetailsTr);
		Elements reservationDetailsTrs = reservationDetailsTr.select("td>table>tbody>tr");
		Elements reservationDetailsKeys = reservationDetailsTrs.get(0).children();
		List<String> keys = new ArrayList<String>();
		for(int i=0;i<reservationDetailsKeys.size();i++){
			keys.add(reservationDetailsKeys.get(i).text());
		}
		Elements reservationDetailsVals = reservationDetailsTrs.get(1).children();
		List<String> vals = new ArrayList<String>();
		for(int i=0;i<reservationDetailsVals.size();i++){
			vals.add(reservationDetailsVals.get(i).text());
		}
		
		int i=0;
		for(String key:keys){
			voucherDetailsMap.put(key, vals.get(i++));
		}
		
		String subTotal = reservationDetailsTrs.get(2).child(1).text();
		System.out.println("subTotal: "+subTotal);
		voucherDetailsMap.put("SubTotal", subTotal);
		
		String taxes = reservationDetailsTrs.get(3).child(1).text();
		System.out.println("taxes: "+taxes);
		voucherDetailsMap.put("Taxes", taxes);
		
		String tds = reservationDetailsTrs.get(4).child(1).text();
		System.out.println("tds: "+tds);
		voucherDetailsMap.put("Tds", tds);
		
		String grandTotal = reservationDetailsTrs.get(5).child(1).text();
		System.out.println("grandTotal: "+grandTotal);
		voucherDetailsMap.put("GrandTotal", grandTotal);
		System.out.println(voucherDetailsMap);
	}
	public static VoucherDetail extractMakeMyTripVoucherDetailsFromMessage(Message message) throws IOException, MessagingException {
		Map<String, String> voucherDetailsMap = new HashMap<String, String>();
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
		String html = sb.toString();
		System.out.println("Make My Trip : "+html);
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
		
		List<Map<String, String>> tariffDetailsList = new ArrayList<Map<String,String>>();
		//System.out.println(voucherDetailsMap);
		VoucherDetailsBuilder builder = new MakeMyTripVoucherDetailsBuilder();
		VoucherDetail voucherDetails = builder.build(voucherDetailsMap, tariffDetailsList );
		voucherDetails.setBookingAgent("makemytrip.com");
		voucherDetails.setPaymentType(PaymentType.ONLINE);
		voucherDetails.setSource(Arrays.toString(message.getRecipients(RecipientType.TO)));
		return voucherDetails;
	}
}
