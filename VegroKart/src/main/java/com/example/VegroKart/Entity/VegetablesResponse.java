package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VegetablesResponse {
	private Long id;
	private String vegetablesName;
	private String quantity;
	private String image;
	private Double price;
}


