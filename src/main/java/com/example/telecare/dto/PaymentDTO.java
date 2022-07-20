package com.example.telecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDTO {
    Long idServicePack;
    int amount;
    int appointmentId;
    String description;
    String bankCode;
}
