package com.example.telecare.service.impl;

import com.example.telecare.config.TwilioConfig;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioServiceImpl implements TwilioService {
    @Autowired
    TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TwilioServiceImpl.class);
    @Override
    public ResponseEntity<?> sendOtp(TwilioRequestDTO twilioRequestDTO) {
        TwilioResponseDTO twilioResponseDTO;
        try {
            PhoneNumber to = new PhoneNumber(twilioRequestDTO.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            //generate otp
            String otp = generateOtp();
            String otpMessage = "Mã xác nhận cho Telecare của bạn là " + otp;

            Message message = Message.creator(
                            to,
                            from,
                            otpMessage)
                    .create();

            otpMap.put(twilioRequestDTO.getPhoneNumber(), otp);
            logger.info("Message {}",message.getDateSent());
            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.DELIVERED, otpMessage);
            return ResponseEntity.ok(twilioResponseDTO);

        } catch (Exception e) {
            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.FAILED, e.getMessage());

            return ResponseEntity.badRequest().body(twilioResponseDTO);
        }

    }

    @Override
    public ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber) {
        if (userInputOtp.equals(otpMap.get(phoneNumber))) {
            otpMap.remove(phoneNumber,userInputOtp);
            return ResponseEntity.ok().body(new ResponseOkMessage("Mã OTP đã được xác thực",new Date()));
        } else {
            throw new BadRequestException("Mã OTP không hợp lệ");
        }

    }

    private String generateOtp() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
