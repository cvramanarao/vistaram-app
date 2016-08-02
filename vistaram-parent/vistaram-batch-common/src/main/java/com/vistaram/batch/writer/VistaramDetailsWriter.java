package com.vistaram.batch.writer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.service.VoucherDetailsService;

public class VistaramDetailsWriter implements ItemWriter<VoucherDetails> {

	@Autowired
	private VoucherDetailsService voucherDetailsService;
	
	@Override
	@Transactional
	public void write(List<? extends VoucherDetails> items) throws Exception {
		System.out.println("VistaramDetailsWriter || writewrite(List<? extends VoucherDetails> items)-->");
		System.out.println("No. of Items : " +items.size());
		for(VoucherDetails voucherDetails : items){
			voucherDetailsService.saveVoucherDetails(voucherDetails);
		}
		
		System.out.println("<-- VistaramDetailsWriter || write(List<? extends VoucherDetails> items)");
		
	}
	
	@Transactional
	public void write(VoucherDetails voucherDetails){
		System.out.println("VistaramDetailsWriter || write(VoucherDetails voucherDetails)-->");
		voucherDetailsService.saveVoucherDetails(voucherDetails);
		System.out.println("<-- VistaramDetailsWriter || write(VoucherDetails voucherDetails)");
	}

}
