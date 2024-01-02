package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.BabyItems;

@Repository
public interface BabyItemsRepository extends JpaRepository<BabyItems, Long>{

	Optional<BabyItems> findById(Integer id);

	List<BabyItems> findByName(String name);

}
