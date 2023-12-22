package com.example.VegroKart.SecurityConfiguration;



import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class TwilioConfig {
	
	 public static final String ACCOUNT_SID = "ACfc3d9d1086732e908fd4677143514361";
	    public static final String AUTH_TOKEN = "df4028903b1edab7add4b1dfde220ac9";
	    public static final String TWILIO_PHONE_NUMBER = "+14433992225"; 

	    static {
	        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	    }
	    
	    public static void sendSms(String toPhoneNumber, String message) {
	        Message twilioMessage = Message.creator(
	                new PhoneNumber(toPhoneNumber),
	                new PhoneNumber(TWILIO_PHONE_NUMBER),
	                message)
	            .create();

	        System.out.println("SMS sent: " + twilioMessage.getSid());
	    }

}
