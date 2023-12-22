package com.example.VegroKart.Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.VegroKart.Dto.ChangePassword;
import com.example.VegroKart.Dto.Login;
import com.example.VegroKart.Dto.Registration;
import com.example.VegroKart.Dto.ResetPassword;
import com.example.VegroKart.Dto.UserDto;
import com.example.VegroKart.Entity.MyAddress;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Helper.ResponseBody;
import com.example.VegroKart.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseBody<?>> saveUser(@Valid
			@RequestParam("file") MultipartFile file,@RequestParam("name") String name,@RequestParam("mobileNumber")String mobileNumber,
			@RequestParam("emailAddress")String emailAddress,@RequestParam("myAddress") String myAddress, @RequestParam("password")String password ) throws SerialException, SQLException, IOException{
	User users=	userService.saveUser(file, name, mobileNumber, myAddress, emailAddress, password);
		ResponseBody<User> body=new ResponseBody<User>();
		body.setStatusCode(HttpStatus.OK.value());
		body.setStatus("SUCCESS");
		body.setData(users);
		return ResponseEntity.ok(body);
	}
	
	
	
	@PostMapping("/registration")
	public ResponseEntity<ResponseBody<?>> saveUser( @RequestBody @Valid Registration registration){
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
	
	 @PostMapping("/verify-otp/{userId}")
	    public boolean verifyOtp(@PathVariable String userId, @RequestParam String userEnteredOtp) {
	        return userService.verifyOtp(userId, userEnteredOtp);
	    }
	
	@PostMapping("/login")
	public ResponseEntity<ResponseBody<?>> userLogin(@Valid @RequestBody Login login){
		userService.loginUser(login.getMobileNumber(), login.getPassword());
		ResponseBody<String> body=new ResponseBody<String>();
		body.setStatusCode(HttpStatus.OK.value());
		body.setStatus("SUCCESS");
		body.setData("User Login Successfull");
		return ResponseEntity.ok(body);
	}
	
	 @PatchMapping("/resetpassword")
	 public ResponseEntity<ResponseBody<?>> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
	        if (resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) {
	             userService.resetPassword(resetPassword.getMobileNumber(), resetPassword.getConfirmPassword());
	            ResponseBody<String> body = new ResponseBody<>();
	            body.setStatusCode(HttpStatus.OK.value());
	            body.setStatus("SUCCESS");
	            body.setData("Password reset successfully!");
	            return ResponseEntity.status(HttpStatus.OK).body(body);
	        } else {
	            throw new UserIsNotFoundException("Confirm password does not match new password");
	        }
	    }
	 
//	 @PatchMapping("/resetpassword/{mobileNumber}")
//	    public ResponseEntity<ResponseBody<User>> resetPassword(
//	            @PathVariable String mobileNumber,
//	            @Valid @RequestBody ResetPassword resetPassword) {
//	        User user = userService.resetPassword(resetPassword);
//	        ResponseBody<User> body = new ResponseBody<>();
//	        body.setStatusCode(HttpStatus.OK.value());
//	        body.setStatus("SUCCESS");
//	        body.setData(user);
//	        return ResponseEntity.status(HttpStatus.OK).body(body);
//	    }


	 @PatchMapping("/changepassword/{mobileNumber}")
	 public ResponseEntity<ResponseBody<?>> changePassword(@PathVariable String mobileNumber, @RequestBody @Valid ChangePassword changePassword) {
	     userService.changePassword(mobileNumber, changePassword);
	     ResponseBody<String> body = new ResponseBody<>();
	     body.setStatusCode(HttpStatus.OK.value());
	     body.setStatus("SUCCESS");
	     body.setData("Password changed successfully!");
	     return ResponseEntity.ok(body);
	 }
	 
	 
	 @GetMapping("/getall")
	 public ResponseEntity<ResponseBody<List<UserDto>>> getAllUser(){
		 List<UserDto> user= userService.getAllUsers();
		 if (user !=null) {
			 ResponseBody<List<UserDto>> body=new ResponseBody<List<UserDto>>();
			 body.setStatusCode(HttpStatus.OK.value());
			 body.setStatus("SUCCESS");
			 body.setData(user);
			 return ResponseEntity.status(HttpStatus.OK).body(body);	
		}else {
			throw new UserIsNotFoundException("Users list empty");
		}
	 }
		 
		 @GetMapping("/getby/{id}")
		 public ResponseEntity<ResponseBody<?>> getUserById(@PathVariable("id") long id) throws SQLException{
			 UserDto user= userService.getUserById(id);
			 if (user !=null) {
				 ResponseBody<UserDto> body=new ResponseBody<UserDto>();
				 body.setStatusCode(HttpStatus.OK.value());
				 body.setStatus("SUCCESS");
				 body.setData(user);
				 return ResponseEntity.status(HttpStatus.OK).body(body);	
			}else {
				throw new UserIsNotFoundException("Users list empty");
			}		 
	      }
		 
		 
		 
		    @GetMapping("/getImageById/{id}")
		    public ResponseEntity<byte[]> getImageById(@PathVariable long id) {
		        byte[] imageBytes = userService.getImageById(id);
		        if (imageBytes != null) {
		            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageBytes);
		        } else {
		        	throw new UserIsNotFoundException("image not founded..");
		        }
		    }
		 
		 
		  
		 
		 @PutMapping("/update/{id}")
		 public ResponseEntity<ResponseBody<User>> updateUserById(@PathVariable("id") long id,
				 @RequestParam("file") MultipartFile file,@RequestParam("name")String name,
				 @RequestParam("mobileNumber")String mobileNumber,@RequestParam("emailAddress") String emailAddress,
				 @RequestParam("myAddress") String myAddress,@RequestParam("password") String password) throws SerialException, IOException, SQLException{
			 User user=userService.updateUser(id, name, emailAddress, mobileNumber, password, file, myAddress);
			 ResponseBody<User> body= new ResponseBody<User>();
			 body.setStatusCode(HttpStatus.OK.value());
			 body.setStatus("SUCCESS");
			 body.setData(user);
			 return ResponseEntity.ok(body);
		 }

		 
		 @DeleteMapping("/delete/{id}")
		 public ResponseEntity<ResponseBody<?>> deleteuserById(@PathVariable("id") long id){
			 userService.deleteUserById(id);
			 ResponseBody<String> body=new ResponseBody<String>();
			 body.setStatusCode(HttpStatus.OK.value());
			 body.setStatus("SUCCESS");
			 body.setData("User deleted successfully");
			 return ResponseEntity.status(HttpStatus.OK).body(body);
		 }
	 
	 
}
