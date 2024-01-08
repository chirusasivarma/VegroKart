package com.example.VegroKart.Entity;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vegetables {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "VegeTables name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "VegeTables name should contain only alphabets")
	private String vegetablesName;
	
	@NotNull(message = "Quantity cannot be empty")
    @Digits(integer = 10, fraction = 0, message = "Quantity must be a whole number")
    @Positive(message = "Quantity must be a positive number")
    private String quantity;  

//    @NotNull(message = "Price cannot be empty")
//    @Positive(message = "Price cannot be empty")
    private Double  price;  

    @Lob
    @JsonIgnore
    private Blob image;

}
	

	    

