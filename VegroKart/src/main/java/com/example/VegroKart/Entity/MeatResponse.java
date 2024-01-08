package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeatResponse {
	private Long id;
	private String meatName;
	private String quantity;
	private String image;
	private Double price;
}



