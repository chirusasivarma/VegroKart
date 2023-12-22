package com.example.VegroKart.Exception;

public class BannerNotFoundException extends RuntimeException {
	private String message;

	public BannerNotFoundException (String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


}