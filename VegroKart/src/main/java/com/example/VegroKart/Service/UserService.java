package com.example.VegroKart.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.VegroKart.Dto.Registration;
import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Helper.EncryptionService;
import com.example.VegroKart.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	public User saveUser(User user) {
		Optional<User> emailOptional=userRepository.findByEmailAddress(user.getEmailAddress());
		if (emailOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this email addres .");
		}
		Optional<User> mobileOptional=userRepository.findByMobileNumber(user.getMobileNumber());
		if (mobileOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this mobile number .");
		}
		user.setPassword(encryptionService.encryptPassword(user.getPassword()));
		return userRepository.save(user);
	}
	
	
	public User userRegistration(Registration registration) {
		Optional<User> emailOptional=userRepository.findByEmailAddress(registration.getEmailAddress());
		if (emailOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this email addres .");
		}
		Optional<User> mobileOptional=userRepository.findByMobileNumber(registration.getMobileNumber());
		if (mobileOptional.isPresent()) {
			throw new UserIsNotFoundException("user is already existed with this mobile number .");
		}
		User user= new User();
		user.setName(registration.getName());
		user.setEmailAddress(registration.getEmailAddress());
		user.setMobileNumber(registration.getMobileNumber());
		user.setPassword(encryptionService.encryptPassword(registration.getPassword()));
		return userRepository.save(user);
	}
	
	
	

}
