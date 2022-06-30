package com.example.telecare.enums;

public enum AppointmentStatus {
    NOT_CONFIRM(1),
    CONFIRMED(2),
    COMPLETE(3),
    CANCEL(4),
    ;

    public int status;

    AppointmentStatus(int value) {
        status = value;
    }
}
