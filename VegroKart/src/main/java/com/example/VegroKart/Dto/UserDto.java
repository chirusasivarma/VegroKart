package com.example.VegroKart.Dto;

import java.sql.Blob;
import java.util.List;

import com.example.VegroKart.Entity.MyAddress;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	
	
	    private long id;
	    
	    private String name;

	    private String emailAddress;

	    private String mobileNumber;


	    private String password;
	   
	    private String myAddress;
	    
	    @Lob
	    private String image;
		
	
}
