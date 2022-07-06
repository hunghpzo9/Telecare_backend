package com.example.telecare.service;

import com.example.telecare.dto.TwilioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TwilioService {

    ResponseEntity sendOtpForPasswordReset(TwilioRequestDTO twilioRequestOTP);

    ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber);
}