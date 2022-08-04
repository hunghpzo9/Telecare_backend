package com.example.telecare;

import com.example.telecare.config.FirebaseConfig;
import com.example.telecare.config.TwilioConfig;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableCaching
public class TelecareApplication {
	@Autowired
	private TwilioConfig twilioConfig;
	@Autowired
	private FirebaseConfig firebaseConfig;
	@PostConstruct
	private void initTwilio(){
		System.out.println("Init twilio");
		Twilio.init(twilioConfig.getAccountSid(),twilioConfig.getAuthToken());
	}
	@PostConstruct
	private void initFirebase(){
		System.out.println("Init firebase");
		firebaseConfig.initialization();
	}


	public static void main(String[] args) {
//		try {
//			String path = "/serviceAccountKey.json";
//			InputStream in = TelecareApplication.class.getResourceAsStream(path);
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(in))
//					.build();
//			FirebaseApp.initializeApp(options);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		SpringApplication.run(TelecareApplication.class, args);
	}

}
