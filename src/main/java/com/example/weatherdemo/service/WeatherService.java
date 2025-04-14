package com.example.weatherdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.weatherdemo.model.DynamicEnum;
import com.example.weatherdemo.model.DynamicEnumFactory;
import com.example.weatherdemo.model.EnumEntry;
import com.example.weatherdemo.model.Weather;

@Service
public class WeatherService {
    
    private static final List<String> CITIES = List.of(
            "New York", "London", "Tokyo", "Paris", "Sydney", 
            "Berlin", "Moscow", "Rio de Janeiro", "Cairo", "Mumbai"
    );
    
    private final Random random = new Random();
    private final DynamicEnumService dynamicEnumService;
    private final DynamicEnumFactory dynamicEnumFactory;
    
    @Autowired
    public WeatherService(DynamicEnumService dynamicEnumService, DynamicEnumFactory dynamicEnumFactory) {
        this.dynamicEnumService = dynamicEnumService;
        this.dynamicEnumFactory = dynamicEnumFactory;
    }
    
    /**
     * Generate random weather for a specific location
     */
    public Weather getRandomWeather(String location) {
        double temperature = getRandomTemperature();
        DynamicEnum condition = getRandomWeatherCondition();
        DynamicEnum temperatureSensation = getRandomTemperatureSensation();
        int humidity = getRandomHumidity();
        double windSpeed = getRandomWindSpeed();
        
        return new Weather(location, temperature, condition, temperatureSensation, humidity, windSpeed);
    }
    
    /**
     * Generate random weather data for a random location
     */
    public Weather getRandomWeather() {
        String randomCity = getRandomCity();
        return getRandomWeather(randomCity);
    }
    
    /**
     * Generate random weather data for multiple cities
     */
    public List<Weather> getRandomWeatherForMultipleLocations(int count) {
        List<Weather> weatherList = new ArrayList<>();
        
        for (int i = 0; i < count; i++) {
            weatherList.add(getRandomWeather());
        }
        
        return weatherList;
    }
    
    // Helper methods to generate random data
    private String getRandomCity() {
        return CITIES.get(random.nextInt(CITIES.size()));
    }
    
    private double getRandomTemperature() {
        // Generate temperature between -15 and 45 degrees Celsius
        return Math.round(ThreadLocalRandom.current().nextDouble(-15, 45) * 10.0) / 10.0;
    }
    
    private DynamicEnum getRandomWeatherCondition() {
        String enumType = Weather.getConditionEnumType();
        // Use dynamic enum values from CSV file
        List<EnumEntry> entries = dynamicEnumService.getEnumsByType(enumType);
        if (entries.isEmpty()) {
            throw new IllegalStateException("No weather types found in enum.csv for type: " + enumType);
        }
        
        // Get a random entry and convert to DynamicEnum
        EnumEntry randomEntry = entries.get(random.nextInt(entries.size()));
        return dynamicEnumFactory.getByValue(enumType, randomEntry.getValue());
    }
    
    private DynamicEnum getRandomTemperatureSensation() {
        String enumType = Weather.getTemperatureSensationEnumType();
        // Use dynamic enum values from CSV file
        List<EnumEntry> entries = dynamicEnumService.getEnumsByType(enumType);
        if (entries.isEmpty()) {
            throw new IllegalStateException("No sensation types found in enum.csv for type: " + enumType);
        }
        
        // Get a random entry and convert to DynamicEnum
        EnumEntry randomEntry = entries.get(random.nextInt(entries.size()));
        return dynamicEnumFactory.getByValue(enumType, randomEntry.getValue());
    }
    
    private int getRandomHumidity() {
        // Generate humidity between 0% and 100%
        return random.nextInt(101);
    }
    
    private double getRandomWindSpeed() {
        // Generate wind speed between 0 and 100 km/h
        return Math.round(ThreadLocalRandom.current().nextDouble(0, 100) * 10.0) / 10.0;
    }
}