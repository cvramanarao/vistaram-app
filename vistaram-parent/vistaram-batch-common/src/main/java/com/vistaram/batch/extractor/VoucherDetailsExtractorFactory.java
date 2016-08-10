package com.vistaram.batch.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherDetailsExtractorFactory {
	
	@Autowired
	public GoIbiboVoucherDetailsExtractor goIbiboVoucherDetailsExtractor;
	
	public VoucherDetailsExtractor getVoucherDetailsExtractor(String from, String subject){
		if (from.contains("hotelpartners@goibibo.com") &&  subject.contains("Confirm Hotel Booking") ) {
			return goIbiboVoucherDetailsExtractor;
		} else {
			return null;
		}
	}

}
