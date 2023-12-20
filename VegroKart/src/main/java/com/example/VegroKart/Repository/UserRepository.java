package com.example.VegroKart.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByMobileNumber(String mobileNumber);
	
	Optional<User> findbyEmailAddress(String emailAddress);
	
	
	
}
