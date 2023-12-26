package com.example.VegroKart.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationRequest {
	
    private String mobileNumber;
    private String otpNumber;
}