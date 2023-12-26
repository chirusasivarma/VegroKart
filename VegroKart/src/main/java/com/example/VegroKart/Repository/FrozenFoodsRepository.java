package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.FrozenFoods;

@Repository
public interface FrozenFoodsRepository extends JpaRepository<FrozenFoods,Long>{

	Optional<FrozenFoods> findById(Integer id);

	List<FrozenFoods> findByName(String name);

}
