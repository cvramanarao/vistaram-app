package com.vistaram.batch.writer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;
import com.vistaram.data.service.VoucherDetailsService;

//@Component
public class VistaramDetailsWriter implements ItemWriter<VoucherDetail> {

	@Autowired
	private VoucherDetailsService voucherDetailsService;
	
	private static Logger logger = LoggerFactory.getLogger(VistaramDetailsWriter.class);
	
	@Override
	public void write(List<? extends VoucherDetail> items) throws Exception {
		logger.debug("VistaramDetailsWriter || writewrite(List<? extends VoucherDetail> items)-->");
		logger.debug("No. of Items : " +items.size());
		for(VoucherDetail voucherDetails : items){
			logger.debug("saving voucher details : "+voucherDetails.getVoucherNumber());
			voucherDetailsService.saveVoucherDetails(voucherDetails);
		}
		
		logger.debug("<-- VistaramDetailsWriter || write(List<? extends VoucherDetail> items)");
		
	}
	

	public void write(VoucherDetail voucherDetails){
		logger.debug("VistaramDetailsWriter || write(VoucherDetail voucherDetails)-->");
		voucherDetailsService.saveVoucherDetails(voucherDetails);
		logger.debug("<-- VistaramDetailsWriter || write(VoucherDetail voucherDetails)");
	}

}
