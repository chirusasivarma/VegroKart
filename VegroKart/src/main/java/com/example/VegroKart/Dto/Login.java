package com.example.VegroKart.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Login {
	
    @NotNull(message = "Mobile number must not be null")
    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit mobile number")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    private String password;

}
