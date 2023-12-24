package com.example.VegroKart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.VegroKart.SecurityConfiguration.TwilioConfig;
import com.twilio.Twilio;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties
@OpenAPIDefinition(info = @Info(description = "vegroKart documention"))
public class VegroKartApplication {
	
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VegroKartApplication.class, args);
	}

}
