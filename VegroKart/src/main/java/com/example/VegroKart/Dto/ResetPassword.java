package com.example.VegroKart.Dto;

import jakarta.validation.constraints.NotBlank;

public class ResetPassword {
	
    @NotBlank(message = "Password must not be blank")
    private String Password;
    
    @NotBlank(message = "Password must not be blank")
    private String confirmPassword;

}
