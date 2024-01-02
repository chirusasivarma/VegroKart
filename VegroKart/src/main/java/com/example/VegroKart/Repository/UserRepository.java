package com.example.VegroKart.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByMobileNumber(String mobileNumber);
	
	Optional<User> findByName(String username);

	Optional<User> findByEmailAddress(String emailAddress);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.instantDelivery WHERE u.id = :userId")
    Optional<User> findByIdWithInstantDelivery(@Param("userId") Long userId);
	
}
