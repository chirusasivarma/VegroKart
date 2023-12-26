package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VegroKart.Entity.DairyProducts;

public interface DairyProductsRepository extends JpaRepository<DairyProducts,Long> {

	Optional<DairyProducts> findById(Integer id);

}