package com.example.VegroKart.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	
	private String name;
    private String mobileNumber;
    private String password;
	private String email;
	private String jwtToken;
    
}
