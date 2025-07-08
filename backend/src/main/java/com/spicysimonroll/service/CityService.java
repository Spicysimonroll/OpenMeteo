package com.spicysimonroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spicysimonroll.entities.City;
import com.spicysimonroll.repos.CityRepository;

@Service
public class CityService {
    
    @Autowired
    private CityRepository repo;

    public List<City> getCities() {
        return repo.findAll();
    }

}
