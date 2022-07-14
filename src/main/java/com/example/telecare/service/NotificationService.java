package com.example.telecare.service;

import org.springframework.http.ResponseEntity;


public interface NotificationService {

   void sendNotification(int uid,String message);

}
