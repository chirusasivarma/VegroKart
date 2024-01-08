package com.example.VegroKart.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.VegroKart.Entity.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {

	Optional<Banner> findById(Integer id);

}
