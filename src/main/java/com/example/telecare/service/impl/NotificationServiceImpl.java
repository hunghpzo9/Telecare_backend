package com.example.telecare.service.impl;

import com.example.telecare.dto.NotificationDTO;
import com.example.telecare.service.NotificationService;
import com.example.telecare.utils.Constants;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service

public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(int uid,String message) {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference collectionReference = db.collection(Constants.COLLECTION_NOTIFICATION)
                .document(String.valueOf(uid))
                .collection(Constants.COLLECTION_NOTIFICATION_MESSAGE);

        NotificationDTO notificationDTO = new NotificationDTO(message, new Date(),false);
        collectionReference.document().set(notificationDTO);

    }

    @Override
    public void sendCloudMessaging(List<String> fcmTokens,String title,String body) throws FirebaseMessagingException {

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .addAllTokens(fcmTokens)
                .build();
        var response = FirebaseMessaging.getInstance().sendMulticast(message);
        System.out.println(response.getSuccessCount() + " messages were sent successfully");
    }

}
