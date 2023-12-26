package com.example.VegroKart.Exception;

public class ProductsIsNotFoundException extends RuntimeException {
	private String message;

	public ProductsIsNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
