package com.example.VegroKart.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Registration {
	

	    @NotBlank(message = "Name cannot be blank")
	    private String name;

	    @NotBlank(message = "Email must not be empty")
	    private String emailAddress;

	    @Pattern(regexp = "^\\+91[0-9]{10}$", message = "Invalid mobile number. It should start with +91 and be followed by 10 digits.")
	    private String mobileNumber;

	    @NotBlank(message = "Password cannot be blank")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
	    private String password;
   
    

}
