package com.example.telecare.service;

import com.example.telecare.dto.TwilioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TwilioService {

    ResponseEntity sendOtp(TwilioRequestDTO twilioRequestDTO);

    ResponseEntity sendSms(TwilioRequestDTO twilioRequestDTO,String message);

    ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber);
}