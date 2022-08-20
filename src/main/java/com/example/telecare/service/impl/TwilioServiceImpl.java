package com.example.telecare.service.impl;

import com.example.telecare.config.TwilioConfig;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.dto.TwilioRequestDTO;
import com.example.telecare.dto.TwilioResponseDTO;
import com.example.telecare.enums.OtpStatus;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.service.TwilioService;
import com.example.telecare.utils.Constants;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class TwilioServiceImpl implements TwilioService {
    @Autowired
    TwilioConfig twilioConfig;

    private static final Integer EXPIRE_MINS = 10;

    private LoadingCache<String, String> otpCache;

    public TwilioServiceImpl() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String s) {
                        return null;
                    }
                });
    }

    @Override
    public ResponseEntity<?> sendOtp(TwilioRequestDTO twilioRequestDTO) {
        TwilioResponseDTO twilioResponseDTO;
        try {
            PhoneNumber to = new PhoneNumber(twilioRequestDTO.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());

            //generate otp
            String otp = generateOtp(twilioRequestDTO.getPhoneNumber());
            String otpMessage = "Mã xác nhận cho ứng dụng Telecare của bạn là " + otp + ".";

             Message.creator(
                            to,
                            from,
                            otpMessage)
                    .create();

            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.DELIVERED, otpMessage);
            return ResponseEntity.ok(twilioResponseDTO);
        } catch (Exception e) {
            twilioResponseDTO = new TwilioResponseDTO(OtpStatus.FAILED, e.getMessage());
            return ResponseEntity.badRequest().body(twilioResponseDTO);
        }
    }

    @Override
    public ResponseEntity sendSms(TwilioRequestDTO twilioRequestDTO, String message) {
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
        if (userInputOtp.equals(getOtp(phoneNumber))) {
            clearOtp(phoneNumber);
            return ResponseEntity.ok().body(new ResponseOkMessage(Constants.OTP_VALID, new Date()));
        } else if (getOtp(phoneNumber).equals(Constants.OTP_EXPIRE_MESSAGE)) {
            throw new BadRequestException(Constants.OTP_EXPIRE_MESSAGE);
        } else {
            throw new BadRequestException(Constants.OTP_INCORRECT);
        }

    }

    private String generateOtp(String key) {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        otpCache.put(key, otp);
        return otp;
    }

    private String getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return Constants.OTP_EXPIRE_MESSAGE;
        }
    }

    private void clearOtp(String key) {
        otpCache.invalidate(key);
    }
}