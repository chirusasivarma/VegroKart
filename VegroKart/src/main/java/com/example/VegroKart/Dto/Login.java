package com.example.VegroKart.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
	

    @Pattern(regexp = "^\\+91[0-9]{10}$", message = "Invalid mobile number. It should start with +91 and be followed by 10 digits.")
    private String mobileNumber;

    @NotBlank(message = "Password cannot be blank")
    private String password;

}
