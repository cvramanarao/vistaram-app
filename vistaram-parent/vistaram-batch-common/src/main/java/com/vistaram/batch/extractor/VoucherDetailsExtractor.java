package com.vistaram.batch.extractor;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import com.vistaram.data.domain.VoucherDetail;

public interface VoucherDetailsExtractor {
	
	
	public VoucherDetail extractVoucherDetails(Message message) throws IOException, MessagingException;

}
