package com.example.VegroKart.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.VegroKart.Helper.ResponseBody;


public class ResponseEntityException extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(UserIsNotFoundException.class)
	public ResponseEntity<ResponseBody<String>> UserIsNotFoundException(UserIsNotFoundException exception){
		ResponseBody<String> body = new ResponseBody<String>();
		body.setStatusCode(HttpStatus.BAD_REQUEST.value());
		body.setStatus("BAD_REQUEST");
		body.setData(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(SnacksIsNotFoundException.class)
	public ResponseEntity<ResponseBody<String>> SnacksIsNotFoundException(SnacksIsNotFoundException exception){
		ResponseBody<String> body = new ResponseBody<String>();
		body.setStatusCode(HttpStatus.BAD_REQUEST.value());
		body.setStatus("BAD_REQUEST");
		body.setData(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	

}
