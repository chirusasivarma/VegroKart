package com.example.VegroKart.OrderForLater;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.User;

@Repository
public interface OrderForLaterRepository  extends JpaRepository<OrderForLater, Long> {
    List<OrderForLater> findByUser(User user);
	List<OrderForLater> findByUserId(Long userId);
}
