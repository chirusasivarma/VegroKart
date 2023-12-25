package com.example.VegroKart.Service;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VegroKart.Dto.Login;
import com.example.VegroKart.Dto.OtpResponseDto;
import com.example.VegroKart.Dto.OtpStatus;
import com.example.VegroKart.Dto.OtpValidationRequest;
import com.example.VegroKart.Dto.Registration;
import com.example.VegroKart.SecurityConfiguration.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmsService {

	@Autowired
    private TwilioConfig twilioConfig;

    private Map<String, String> otpMap = new HashMap<>();

    public OtpResponseDto sendSMS(Login login) {
        OtpResponseDto otpResponseDto;
        try {
            PhoneNumber to = new PhoneNumber(login.getMobileNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String otp = generateOTP();
            String otpMessage = "Your OTP is " + otp + ". Use this to complete the login process.";
            otpMap.put(login.getMobileNumber(), otp);
            Message message = Message.creator(to, from, otpMessage).create();
            otpResponseDto = new OtpResponseDto(OtpStatus.VERIFIED, "OTP sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }
    
    public OtpResponseDto sendRegistrationOtp(Registration registration) {
        OtpResponseDto otpResponseDto;
        try {
            PhoneNumber to = new PhoneNumber(registration.getMobileNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
            String otp = generateOTP();
            String otpMessage = "Your registration OTP is " + otp + ". Use this to complete the registration process.";
            Message message = Message.creator(to, from, otpMessage).create();
            otpMap.put(registration.getMobileNumber(), otp);
            otpResponseDto = new OtpResponseDto(OtpStatus.VERIFIED, "Registration OTP sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;
    }
    
    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> mobileNumbers = otpMap.keySet();
        String enteredMobileNumber = otpValidationRequest.getMobileNumber();

        if (mobileNumbers.contains(enteredMobileNumber)) {
            otpMap.remove(enteredMobileNumber, otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }
    
    public String validateOtp(String mobileNumber, String otpNumber) {
        try {
            String storedOtp = otpMap.get(mobileNumber);

            if (StringUtils.isBlank(storedOtp)) {
                return "OTP is invalid!";
            }

            if (otpNumber.equals(storedOtp)) {
                // OTP is correct, you can proceed with the login
                otpMap.remove(mobileNumber);
                return "OTP is valid!";
            } else {
                // Incorrect OTP
                return "OTP is invalid!";
            }
        } catch (Exception e) {
            log.error("Error validating OTP: {}", e.getMessage());
            return "An error occurred while validating OTP.";
        }
    }



//    public String validateOtp(Login login) {
//        String storedOtp = otpMap.get(login.getMobileNumber());
//        if (storedOtp != null && storedOtp.equals(login.getOtp())) {
//            otpMap.remove(login.getMobileNumber());
//            return "OTP is valid for mobile number: " + login.getMobileNumber();
//        } else {
//            return "OTP is invalid!";
//        }
//    }
//
    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

	
}
