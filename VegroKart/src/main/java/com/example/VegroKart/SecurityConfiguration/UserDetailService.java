package com.example.VegroKart.SecurityConfiguration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.VegroKart.Entity.User;
import com.example.VegroKart.Exception.UserIsNotFoundException;
import com.example.VegroKart.Repository.UserRepository;

@Component
public class UserDetailService  implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional=userRepository.findByName(username);
		return userOptional.map(LocalUserService::new)
				.orElseThrow(()-> new UserIsNotFoundException("user is not found " + username));  
	}

}
