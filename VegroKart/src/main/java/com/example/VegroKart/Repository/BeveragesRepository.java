package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VegroKart.Entity.Beverages;

public interface BeveragesRepository extends JpaRepository <Beverages,Long> {

	List<Beverages> findByBeveragesName(String beveragesName);

	Optional<Beverages> findById(Integer id);

}
