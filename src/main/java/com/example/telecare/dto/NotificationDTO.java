package com.example.telecare.dto;


import com.google.firebase.database.PropertyName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDTO {
    private String message;
    private Date time;
    @PropertyName(value ="isRead")
    private boolean isRead;
}
