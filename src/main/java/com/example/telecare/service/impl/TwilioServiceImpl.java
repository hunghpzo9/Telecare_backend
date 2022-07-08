package com.example.telecare.service.impl;

import com.example.telecare.config.TwilioConfig;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.dto.TwilioRequestDTO;
import com.example.telecare.dto.TwilioResponseDTO;
import com.example.telecare.enums.OtpStatus;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
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

    @Override
    public ResponseEntity<?> sendOtp(TwilioRequestDTO twilioRequestDTO) {
        TwilioResponseDTO twilioResponseDTO;
        try {
            PhoneNumber to = new PhoneNumber(twilioRequestDTO.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            //generate otp
            String otp = generateOtp();
            String otpMessage = "Mã xác nhận của bạn là " + otp;

             Message.creator(
                            to,
                            from,
                            otpMessage)
                    .create();
            otpMap.put(twilioRequestDTO.getPhoneNumber(), otp);

            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.DELIVERED, otpMessage);
            return ResponseEntity.ok(twilioResponseDTO);
        } catch (Exception e) {
            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.FAILED, e.getMessage());
            return ResponseEntity.badRequest().body(twilioResponseDTO);
        }
    }

    @Override
    public ResponseEntity sendSms(TwilioRequestDTO twilioRequestDTO,String message) {
        TwilioResponseDTO twilioResponseDTO;
        try {
            PhoneNumber to = new PhoneNumber(twilioRequestDTO.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());



            Message.creator(
                            to,
                            from,
                            message)
                    .create();

            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.DELIVERED, message);
            return ResponseEntity.ok(twilioResponseDTO);
        } catch (Exception e) {
            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.FAILED, e.getMessage());
            return ResponseEntity.badRequest().body(twilioResponseDTO);
        }
    }

    @Override
    public ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber) {
        if (userInputOtp.equals(otpMap.get(phoneNumber))) {
            return ResponseEntity.ok().body(new ResponseOkMessage("Mã OTP đã được xác thực",new Date()));
        } else {
            throw new BadRequestException("Mã OTP không hợp lệ");
        }

    }

    private String generateOtp() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}