package com.example.VegroKart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.Dto.Registration;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<?>> saveUser(@RequestBody User user){
	User users=	userService.saveUser(user);
		ResponseBody<User> body=new ResponseBody<User>();
		body.setStatusCode(HttpStatus.OK.value());
		body.setStatus("SUCCESS");
		body.setData(users);
		return ResponseEntity.ok(body);
	}
	
	
	
	@PostMapping("/registration")
	public ResponseEntity<ResponseBody<?>> saveUser(@RequestBody Registration registration){
	User users=	userService.userRegistration(registration);
	if (users!=null) {
		ResponseBody<User> body=new ResponseBody<User>();
		body.setStatusCode(HttpStatus.OK.value());
		body.setStatus("SUCCESS");
		body.setData(users);
		return ResponseEntity.ok(body);
	}else {
		throw new UserIsNotFoundException("User registration failed");
	}
		
	}

}
