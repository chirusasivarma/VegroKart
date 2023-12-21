package com.example.VegroKart.Exception;

public class SnacksIsNotFoundException extends RuntimeException{
	
	private String message;

	public SnacksIsNotFoundException(String message) {
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