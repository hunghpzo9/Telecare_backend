package com.example.telecare.enums;

public enum FeedbackStatus {
    HIDDEN_FEEDBACK(0),

    SHOW_FEEDBACK(1);

    final int status;

     FeedbackStatus(int status) {
        this.status = status;
    }
}
