package com.example.VegroKart.Exception;

public class DairyProductsIsNotFoundException extends RuntimeException {
	private String message;

	public DairyProductsIsNotFoundException(String message) {
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
