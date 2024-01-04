package com.example.VegroKart.Dto;

import java.sql.Blob;
import java.util.List;

import com.example.VegroKart.Entity.MyAddress;
import com.example.VegroKart.InstantDelivery.InstantDelivery;
import com.example.VegroKart.MorningDelivery.MorningDelivery;
import com.example.VegroKart.OrderForLater.OrderForLater;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
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
	   
	    private List<MyAddress> myAddress;
	    
	    @Lob
	    private String image;
	    
	 
	    private List<InstantDelivery> instantDelivery;
	    
	  
	    private List<MorningDelivery> morningDeliveries; 

	    private List<OrderForLater> orderForLater;

		
			
		
		
	
}
