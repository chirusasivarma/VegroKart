package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.PetFood;
@Repository
public interface PetFoodRepository extends JpaRepository<PetFood, Long> {

	Optional<PetFood> findById(Integer id);

	List<PetFood> findByFoodName(String foodname);

}
