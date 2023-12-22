package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VegroKart.Entity.Snacks;

public interface SnacksRepository extends JpaRepository<Snacks,Long> {

	Optional<Snacks> findById(Integer id);

	List<Snacks> findBySnacksName(String snacksName);

}
