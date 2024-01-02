package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FruitsResponse {
	private Long id;
	private String fruitName;
	private String quantity;
	private String image;
	private String price;
}
