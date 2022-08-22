package com.example.telecare.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Configuration;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {
    public void initialization() {
        try {
            String path = "/serviceAccountKey.json";
            InputStream in = this.getClass().getResourceAsStream(path);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(in))
                    .build();
            var app = FirebaseApp.initializeApp(options);
            FirebaseMessaging.getInstance(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
