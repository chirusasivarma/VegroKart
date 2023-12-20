package com.example.VegroKart.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Registration {
	
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotEmpty(message = "email address must not be empty")
   	@NotNull(message = "email address must not be null")
   	@Email(message = "please provide valide email")
    private String emailAddress;

    @NotNull(message = "Mobile number must not be null")
    @Pattern(regexp = "\\d{10}", message = "Please provide a valid 10-digit mobile number")
    private String mobileNumber;

    @NotBlank(message = "Password must not be blank")
    private String password;

}
