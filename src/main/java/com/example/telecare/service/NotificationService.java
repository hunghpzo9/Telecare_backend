package com.example.telecare.service;

import com.google.firebase.messaging.FirebaseMessagingException;

import java.util.List;

public interface NotificationService {

   void sendNotification(int uid,String message);

   void sendCloudMessaging(List<String> fcmTokens, String title, String body) throws FirebaseMessagingException;

}
