package com.example.VegroKart.Service;

import java.security.SecureRandom;


import com.example.VegroKart.SecurityConfiguration.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtpService {


	    private static final int OTP_LENGTH = 6; // Set the desired length of the OTP

	    public static void sendOtpViaSms(String mobileNumber, String otp) {
	        try {
	            String messageBody = "Your OTP is: " + otp;

	            Message message = Message.creator(
	                    new PhoneNumber(mobileNumber),
	                    new PhoneNumber(TwilioConfig.TWILIO_PHONE_NUMBER),
	                    messageBody
	            ).create();

	            log.info("OTP sent successfully. SID: {}", message.getSid());
	        } catch (Exception e) {
	        	log.error("Error sending OTP via SMS: {}", e.getMessage());
	            // Handle exception (e.g., log, throw custom exception, etc.)
	        }
	    }

	    public static boolean verifyOtp(String userEnteredOtp, String storedOtp) {
	        try {
	            // Check if the entered OTP matches the stored OTP
	            return userEnteredOtp.equals(storedOtp);
	        } catch (Exception e) {
	        	log.error("Error verifying OTP: {}", e.getMessage());
	            // Handle exception (e.g., log, throw custom exception, etc.)
	            return false;
	        }
	    }

	    public static String generateRandomNumericOtp() {
	        try {
	            SecureRandom secureRandom = new SecureRandom();
	            StringBuilder otpBuilder = new StringBuilder();

	            for (int i = 0; i < OTP_LENGTH; i++) {
	                otpBuilder.append(secureRandom.nextInt(10)); // Append a random digit (0-9)
	            }

	            return otpBuilder.toString();
	        } catch (Exception e) {
	        	log.error("Error generating random numeric OTP: {}", e.getMessage());
	            // Handle exception (e.g., log, throw custom exception, etc.)
	            return null;
	        }
	    }
	
}
