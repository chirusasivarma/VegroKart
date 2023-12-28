package com.example.VegroKart.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
	
	@NotBlank(message = "Confirm password must not be blank")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String oldPassword;
	
	@NotBlank(message = "Confirm password must not be blank")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String newPassword;
	
	@NotBlank(message = "Confirm password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
	private String confirmPassword;	 

}
