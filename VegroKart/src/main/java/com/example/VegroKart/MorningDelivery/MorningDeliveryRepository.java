package com.example.VegroKart.MorningDelivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.User;
import com.example.VegroKart.InstantDelivery.InstantDelivery;

@Repository
public interface MorningDeliveryRepository extends JpaRepository<MorningDelivery, Long> {

	  List<MorningDelivery> findByUser(User user);
	  List<MorningDelivery> findByUserId(Long userId);
}
