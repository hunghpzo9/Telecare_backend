package com.example.telecare.enums;

public enum PaymentStatus {
    PENDING(1),
    PAID(2),
    ;

    public int status;

    PaymentStatus(int value) {
        status = value;
    }
}
