package com.example.VegroKart.Exception;

public class FruitsIsNotFoundException extends RuntimeException {
	private String message;

	public FruitsIsNotFoundException(String message) {
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
