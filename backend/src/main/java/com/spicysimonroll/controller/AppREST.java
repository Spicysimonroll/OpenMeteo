package com.spicysimonroll.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spicysimonroll.entities.City;
import com.spicysimonroll.service.CityService;
import com.spicysimonroll.service.WeatherService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*") // Allow frontend calls (or restrict by domain)
public class AppREST {
    
    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CityService cityService;

    @GetMapping("weather")
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        
        try {
            return ResponseEntity.ok(weatherService.getWeatherForCity(city));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
        }

    }

    @GetMapping("cities")
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.getCities());
    }
}