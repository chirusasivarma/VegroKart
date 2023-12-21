package com.example.VegroKart.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerResponse {
	private long id;
	private String banner1;
	private String banner2;
	private String banner3;
	private String banner4;
	private String banner5;

}
