package com.example.VegroKart.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.VegroKart.Helper.ResponseBody;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@RestControllerAdvice
public class ValidationExceptionHandler {

	
	@ResponseStatus(code =  HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex){
		Map<String, String>errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
		errorMap.put(error.getField(), error.getDefaultMessage());
			});
		return errorMap;
	}
	

	
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
	@ExceptionHandler(ProductsIsNotFoundException.class)
	public ResponseEntity<ResponseBody<String>> ProductsIsNotFoundException(ProductsIsNotFoundException exception){
		ResponseBody<String> body = new ResponseBody<String>();
		body.setStatusCode(HttpStatus.BAD_REQUEST.value());
		body.setStatus("BAD_REQUEST");
		body.setData(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(FruitsIsNotFoundException.class)
	public ResponseEntity<ResponseBody<String>> FruitsIsNotFoundException(FruitsIsNotFoundException exception){
		ResponseBody<String> body = new ResponseBody<String>();
		body.setStatusCode(HttpStatus.BAD_REQUEST.value());
		body.setStatus("BAD_REQUEST");
		body.setData(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	

}

	
	
	

