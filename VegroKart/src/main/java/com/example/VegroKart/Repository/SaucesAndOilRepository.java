package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.example.VegroKart.Entity.SaucesAndOil;

@RestController

public interface SaucesAndOilRepository extends JpaRepository<SaucesAndOil,Long>{

	List<SaucesAndOil> findByName(String name);

	Optional<SaucesAndOil> findById(Integer id);

}
