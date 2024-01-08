package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaucesAndOilResponse {
	private Long id;
	private String name;
	private String quantity;
	private String image;
	private Double price;
}