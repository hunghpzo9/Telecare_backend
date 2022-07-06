package com.example.telecare.service;

import com.example.telecare.dto.TwilioRequestDTO;
import org.springframework.http.ResponseEntity;

public interface Twilio {

    ResponseEntity sendOtp(TwilioRequestDTO twilioRequestDTO);

    ResponseEntity sendSmsToDoctor(TwilioRequestDTO twilioRequestDTO);

    ResponseEntity<?> validateOtp(String userInputOtp, String phoneNumber);
}