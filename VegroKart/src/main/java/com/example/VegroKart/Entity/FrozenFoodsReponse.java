package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrozenFoodsReponse {
	private Long id;
	private String name;
	private int quantity;
	private String image;
	private double price;
}

