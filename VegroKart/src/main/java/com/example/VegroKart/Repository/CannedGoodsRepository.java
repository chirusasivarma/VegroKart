package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.CannedGoods;

@Repository
public interface CannedGoodsRepository extends JpaRepository<CannedGoods,Long> {

	Optional<CannedGoods> findById(Integer id);

	List<CannedGoods> findByName(String name);

	

}
