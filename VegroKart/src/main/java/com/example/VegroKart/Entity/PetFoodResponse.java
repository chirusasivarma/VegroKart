package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetFoodResponse {
	
	private Long id;
	private String foodName;
	private String quantity;
	private String image;
	private String price;
}
