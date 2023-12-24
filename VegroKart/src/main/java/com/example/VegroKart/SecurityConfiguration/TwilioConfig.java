package com.example.VegroKart.SecurityConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.example.VegroKart.SecurityConfiguration.TwilioConfig;


import lombok.Data;

@Configuration
@Component
@ConfigurationProperties(prefix = "twilio")
@Data
public class TwilioConfig {
	
    private String accountSid;
    private String authToken;
    private String phoneNumber;

		

	    

}
