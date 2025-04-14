package com.example.weatherdemo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.weatherdemo.model.Weather;
import com.example.weatherdemo.model.WeatherType;
import com.example.weatherdemo.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/random")
    public ResponseEntity<Weather> getRandomWeather() {
        return ResponseEntity.ok(weatherService.getRandomWeather());
    }

    @GetMapping("/random/{location}")
    public ResponseEntity<Weather> getRandomWeatherForLocation(@PathVariable String location) {
        return ResponseEntity.ok(weatherService.getRandomWeather(location));
    }

    @GetMapping("/random/multiple/{count}")
    public ResponseEntity<List<Weather>> getMultipleRandomWeather(@PathVariable int count) {
        if (count < 1 || count > 20) {
            count = 5; // Default to 5 if out of reasonable range
        }
        return ResponseEntity.ok(weatherService.getRandomWeatherForMultipleLocations(count));
    }

    @GetMapping("/types")
    public ResponseEntity<List<String>> getWeatherTypes() {
        List<String> types = Arrays.stream(WeatherType.values())
                .map(WeatherType::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }
}