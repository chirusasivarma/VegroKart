package com.example.VegroKart.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
	
	@NotBlank(message = "Confirm password must not be blank")
	private String oldPassword;
	
	@NotBlank(message = "Confirm password must not be blank")
	private String newPassword;
	
	@NotBlank(message = "Confirm password must not be blank")
	private String confirmPassword;	 

}
