package com.example.VegroKart.InstantDelivery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.User;



@Repository
public interface InstantDeliveryRepository extends JpaRepository<InstantDelivery, Long> {
    List<InstantDelivery> findByUser(User user);
	List<InstantDelivery> findByUserId(Long userId);

}
