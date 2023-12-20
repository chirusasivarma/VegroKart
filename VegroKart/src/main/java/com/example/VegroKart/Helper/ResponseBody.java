package com.example.VegroKart.Helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody<T> {
	
	private int statusCode;
	
	private String status;
	
	private T data;

}
