package com.vistaram.batch.extractor;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.springframework.stereotype.Component;

import com.vistaram.batch.utils.VistaramMessageUtils;
import com.vistaram.data.domain.VoucherDetail;

@Component
public class GoIbiboVoucherDetailsExtractor implements VoucherDetailsExtractor {

	@Override
	public VoucherDetail extractVoucherDetails(Message message) throws IOException,
			MessagingException {
		return VistaramMessageUtils.extractGoIbiboVoucherDetailsFromMessage(message);

	}

}
