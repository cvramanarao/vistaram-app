package com.vistaram.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.vistaram.data.domain.VoucherDetails;
import com.vistaram.data.service.VoucherDetailsService;

public class VistaramDetailsWriter implements ItemWriter<VoucherDetails> {

	@Autowired
	private VoucherDetailsService voucherDetailsService;
	
	@Override
	public void write(List<? extends VoucherDetails> items) throws Exception {
		System.out.println("VistaramDetailsWriter || write()-->");
		
		for(VoucherDetails voucherDetails : items){
			voucherDetailsService.saveVoucherDetails(voucherDetails);
		}
		
		System.out.println("<-- VistaramDetailsWriter || write()");
		
	}

}
