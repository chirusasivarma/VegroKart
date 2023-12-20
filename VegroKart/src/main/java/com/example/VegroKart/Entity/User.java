package com.example.VegroKart.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

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
