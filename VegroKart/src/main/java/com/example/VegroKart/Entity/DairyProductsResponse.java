package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DairyProductsResponse {
	private Long id;
	private String productName;
	private int quantity;
	private String image;
	private double price;
}


