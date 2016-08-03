package com.vistaram.batch.writer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.service.VoucherDetailsService;

public class VistaramDetailsWriter implements ItemWriter<VoucherDetail> {

	@Autowired
	private VoucherDetailsService voucherDetailsService;
	
	@Override
	@Transactional
	public void write(List<? extends VoucherDetail> items) throws Exception {
		System.out.println("VistaramDetailsWriter || writewrite(List<? extends VoucherDetail> items)-->");
		System.out.println("No. of Items : " +items.size());
		for(VoucherDetail voucherDetails : items){
			voucherDetailsService.saveVoucherDetails(voucherDetails);
		}
		
		System.out.println("<-- VistaramDetailsWriter || write(List<? extends VoucherDetail> items)");
		
	}
	
	@Transactional
	public void write(VoucherDetail voucherDetails){
		System.out.println("VistaramDetailsWriter || write(VoucherDetail voucherDetails)-->");
		voucherDetailsService.saveVoucherDetails(voucherDetails);
		System.out.println("<-- VistaramDetailsWriter || write(VoucherDetail voucherDetails)");
	}

}
