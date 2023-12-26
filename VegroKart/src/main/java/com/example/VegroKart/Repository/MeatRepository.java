package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.Meat;

@Repository
public interface  MeatRepository extends JpaRepository<Meat,Long> {

	Optional<Meat> findById(Integer id);

	List<Meat> findByMeatName(String meatName);

}
