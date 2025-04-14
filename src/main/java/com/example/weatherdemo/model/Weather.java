package com.example.weatherdemo.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Weather information")
public class Weather {
    @Schema(description = "Location name", example = "New York")
    private String location;
    
    @Schema(description = "Temperature in Celsius", example = "22.5")
    private Double temperature;
    
    @Schema(description = "Weather condition", example = "SUNNY")
    private WeatherType condition;
    
    @Schema(description = "Humidity percentage", example = "65")
    private Integer humidity;
    
    @Schema(description = "Wind speed in km/h", example = "10.2")
    private Double windSpeed;
    
    @Schema(description = "Timestamp of weather reading", example = "2025-04-14T14:30:00")
    private LocalDateTime timestamp;

    public Weather() {
    }

    public Weather(String location, Double temperature, WeatherType condition, Integer humidity, Double windSpeed) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public WeatherType getCondition() {
        return condition;
    }

    public void setCondition(WeatherType condition) {
        this.condition = condition;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}