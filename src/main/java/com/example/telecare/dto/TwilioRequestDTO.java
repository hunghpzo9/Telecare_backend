package com.example.telecare.dto;

import lombok.Data;

@Data
public class TwilioRequestDTO {
    private String phoneNumber;
    private String otp;
}
