package com.example.telecare.service;

import org.springframework.http.ResponseEntity;

public interface TwilioService {

    ResponseEntity sendOtp(TwilioRequestDTO twilioRequestOTP);

    ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber);
}
