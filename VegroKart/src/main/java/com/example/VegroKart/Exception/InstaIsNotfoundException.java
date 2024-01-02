package com.example.VegroKart.Exception;

public class InstaIsNotfoundException extends RuntimeException {
	private String message;

	public InstaIsNotfoundException(String message) {
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
