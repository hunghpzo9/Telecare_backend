package com.example.telecare.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    public void initialization(){
        FileInputStream serviceAccount;
        try{
            File file = ResourceUtils.getFile("serviceAccountKey.json");
            serviceAccount = new FileInputStream(file.getAbsolutePath());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
