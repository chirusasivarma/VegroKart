package com.example.VegroKart.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.VegroKart.Entity.PersonalCare;

public interface PersonalCareRepository extends JpaRepository<PersonalCare,Long>{

	Optional<PersonalCare> findById(Integer id);

	List<PersonalCare> findByName(String name);

}
