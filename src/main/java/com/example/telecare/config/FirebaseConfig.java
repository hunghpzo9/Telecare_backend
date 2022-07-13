package com.example.telecare.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

@Configuration
public class FirebaseConfig {
    public void initialization(){
        FileInputStream serviceAccount;
        try{
            ClassLoader classLoader = FirebaseConfig.class.getClassLoader();
            File file1=new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
            File file = ResourceUtils.getFile("classpath:serviceAccountKey.json");
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
