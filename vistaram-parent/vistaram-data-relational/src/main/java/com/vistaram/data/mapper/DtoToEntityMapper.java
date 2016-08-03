package com.vistaram.data.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.vistaram.data.domain.PaymentType;
import com.vistaram.data.domain.TariffDetails;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.relational.domain.BookingDetail;
import com.vistaram.data.relational.domain.GuestDetail;
import com.vistaram.data.relational.domain.HotelDetail;
import com.vistaram.data.relational.domain.RoomDetail;
import com.vistaram.data.relational.domain.TariffDetail;

public class DtoToEntityMapper {
	
	
	public static  BookingDetail mapVoucherDetailsToBookingDetails(
			VoucherDetail voucherDetails) {
		
		
		System.out.println("mapVoucherDetailsToBookingDetails()-->");
		BookingDetail bookingDetail = new BookingDetail();
		bookingDetail.setBookingAgent(voucherDetails.getBookingAgent());
		bookingDetail.setVoucherId(voucherDetails.getVoucherNumber());
		bookingDetail.setBookingDate(voucherDetails.getBookingDate());
		bookingDetail.setCheckinDate(voucherDetails.getCheckInDate());
		
		bookingDetail.setCheckoutDate(voucherDetails.getCheckOutDate());
		bookingDetail.setNoOfRooms(voucherDetails.getNoOfRooms());
		
		bookingDetail.setNoOfNights(voucherDetails.getNoOfNights());
		//bookingDetail.setRatePlanId(voucherDetails.getRatePlan());
		
		List<RoomDetail> roomDetails = mapVoucherDetailsToRoomDetails(voucherDetails);
		for(RoomDetail roomDetail : roomDetails) {
			roomDetail.setBookingDetail(bookingDetail);
		}
		bookingDetail.setRoomDetails(roomDetails);
		GuestDetail guestDetail = mapVoucherDetailsToGuestDetails(voucherDetails);
		List<BookingDetail> bookingDetailsList = new ArrayList<BookingDetail>();
		bookingDetailsList.add(bookingDetail);
		guestDetail.setBookingDetails(bookingDetailsList);
		bookingDetail.setGuestDetail(guestDetail);
		List<TariffDetail> tariffDetails = mapVoucherDetailsToTariffDetails(voucherDetails);
		for(TariffDetail tariffDetail : tariffDetails){
			tariffDetail.setBookingDetail(bookingDetail);
		}
		bookingDetail.setTariffDetails(tariffDetails);
		bookingDetail.setTotalTax(voucherDetails.getTotalTax());
		bookingDetail.setTotalAmout(voucherDetails.getTotalAmountPayable());
		bookingDetail.setPaymentType(PaymentType.ONLINE.toString());
		System.out.println("<-- mapVoucherDetailsToBookingDetails()");
		return bookingDetail;
		
	}
	
	
	public static List<TariffDetail> mapVoucherDetailsToTariffDetails(VoucherDetail voucherDetails){
		List<TariffDetail> tariffDetails = new ArrayList<TariffDetail>();
		List<TariffDetails> tariffDetailsList = voucherDetails.getTariffDetails();
		for(TariffDetails details : tariffDetailsList) {
		
			TariffDetail tariffDetail = new TariffDetail();
			tariffDetail.setCheckinDate(details.getCheckInDate());
			tariffDetail.setCheckoutDate(details.getCheckOutDate());
			tariffDetail.setNoOfNights(details.getNoOfNights());
			tariffDetail.setRatePerRoom(details.getRatePerRoom());
			tariffDetail.setTotalRate(details.getRoomRate());
			tariffDetails.add(tariffDetail);
		}
		
		
		return tariffDetails;
	}
	public static GuestDetail mapVoucherDetailsToGuestDetails(VoucherDetail voucherDetails){
		GuestDetail guestDetail = new GuestDetail();
		String guestName = voucherDetails.getGuestName();
		int beginIndex =  guestName.lastIndexOf(" ");
		if(beginIndex > -1) {
		guestDetail.setFirstName(guestName.substring(0, beginIndex));
		guestDetail.setLastName(guestName.substring(beginIndex));
		} else {
			guestDetail.setFirstName(guestName);
			guestDetail.setLastName(" ");
		}
		
		return guestDetail;
	}
	public static List<RoomDetail> mapVoucherDetailsToRoomDetails(VoucherDetail voucherDetails){
		List<RoomDetail> roomDetails = new ArrayList<RoomDetail>();
		for(int i=0;i<voucherDetails.getNoOfRooms();i++) {
			RoomDetail roomDetail = new RoomDetail();
			
			for(Map.Entry<String, Map<String, Integer>> entry : voucherDetails.getGuestsPerRoom().entrySet()){
				roomDetail.setRoomName(entry.getKey());
				roomDetail.setNoOfAdults(entry.getValue().get("Adult"));
				roomDetail.setNoOfAdults(entry.getValue().get("Child"));
			}
			//roomDetail.setRoomRate(voucherDetails.get);
			roomDetail.setRoomType(voucherDetails.getRoomType());
			roomDetails.add(roomDetail);
		}
		return roomDetails;
		
	}

}
