package com.example.telecare.dto;

import com.example.telecare.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwilioResponseDTO {
    private OtpStatus status;
    private String message;
}
