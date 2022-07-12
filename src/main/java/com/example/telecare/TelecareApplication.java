package com.example.telecare;

import com.example.telecare.config.FirebaseConfig;
import com.example.telecare.config.TwilioConfig;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.Arrays;

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
		SpringApplication.run(TelecareApplication.class, args);
	}

}
