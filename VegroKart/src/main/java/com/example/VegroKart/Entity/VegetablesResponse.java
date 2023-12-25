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
	private int quantity;
	private String image;
	private double price;
}


