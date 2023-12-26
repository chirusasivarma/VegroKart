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
public class ResetPassword {
	


    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Confirm password must not be blank")
    private String confirmPassword;

}
