package com.spicysimonroll.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spicysimonroll.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByNameIgnoreCase(String name);
}
