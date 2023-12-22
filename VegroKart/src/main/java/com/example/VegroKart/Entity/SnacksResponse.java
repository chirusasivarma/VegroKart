package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnacksResponse {
	private Long id;
	private String snacksName;
	private int quantity;
	private String image;
	private double price;
}
