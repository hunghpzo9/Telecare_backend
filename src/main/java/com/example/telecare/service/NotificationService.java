package com.example.telecare.service;

import org.springframework.http.ResponseEntity;


public interface NotificationService {

    ResponseEntity<?> sendNotification(int uid,String message);

}
