package com.spicysimonroll.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.spicysimonroll.entities.City;
import com.spicysimonroll.repos.CityRepository;

@Service
public class WeatherService {
    
    @Autowired
    private CityRepository cityRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getWeatherForCity(String cityName) {
    
        City city = cityRepository.findByNameIgnoreCase(cityName)
                .orElseThrow(() -> new RuntimeException("City not found"));

        double lat = city.getLatitude();
        double lon = city.getLongitude();

        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&hourly=temperature_2m", lat, lon);

        Map<String, Object> apiResponse = restTemplate.getForObject(url, Map.class);
        Map<String, Object> hourly = (Map<String, Object>) apiResponse.get("hourly");

        // Filter response
        Map<String, Object> filtered = new HashMap<>();

        filtered.put("time", hourly.get("time"));
        filtered.put("temperature", hourly.get("temperature_2m"));

        return filtered;
    }

}
