package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VegroKart.Entity.Vegetables;

public interface VegetablesRepository extends JpaRepository<Vegetables,Long> {

	List<Vegetables> findByVegetablesName(String vegetablesName);

	Optional<Vegetables> findById(Integer id);

}
