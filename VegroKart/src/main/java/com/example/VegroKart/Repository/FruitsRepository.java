package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.Fruits;

@Repository
public interface FruitsRepository extends JpaRepository<Fruits,Long> {

	Optional<Fruits> findById(Integer id);

	List<Fruits> findByFruitName(String fruitName);

}
