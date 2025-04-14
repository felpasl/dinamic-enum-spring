package com.example.weatherdemo.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Weather information")
public class Weather {
    @Schema(description = "Location name", example = "New York")
    private String location;
    
    @Schema(description = "Temperature in Celsius", example = "22.5")
    private Double temperature;
    
    @Schema(
        description = "Weather condition", 
        example = "SUNNY", 
        type = "string",
        ref = "#/components/schemas/weatherTypeEnum"
    )
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private DynamicEnum condition;
    
    // Specify which enum type to use for this weather condition
    @Schema(hidden = true)
    private final static String CONDITION_ENUM_TYPE = "weatherType";
    
    @Schema(
        description = "Temperature sensation", 
        example = "MILD", 
        type = "string",
        ref = "#/components/schemas/temperatureSensationEnum"
    )
    private DynamicEnum temperatureSensation;
    
    // Specify which enum type to use for temperature sensation
    @Schema(hidden = true)
    private final static String TEMPERATURE_SENSATION_ENUM_TYPE = "temperatureSensation";
    
    @Schema(description = "Humidity percentage", example = "65")
    private Integer humidity;
    
    @Schema(description = "Wind speed in km/h", example = "10.2")
    private Double windSpeed;
    
    @Schema(description = "Timestamp of weather reading", example = "2025-04-14T14:30:00")
    private LocalDateTime timestamp;

    public Weather() {
    }

    public Weather(String location, Double temperature, DynamicEnum condition, DynamicEnum temperatureSensation,
                  Integer humidity, Double windSpeed) {
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        this.temperatureSensation = temperatureSensation;
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

    public DynamicEnum getCondition() {
        return condition;
    }

    public void setCondition(DynamicEnum condition) {
        // Validate that the enum type is correct
        if (condition != null && !CONDITION_ENUM_TYPE.equals(condition.getEnumType())) {
            throw new IllegalArgumentException("Expected enum type: " + CONDITION_ENUM_TYPE + 
                                             ", but got: " + condition.getEnumType());
        }
        this.condition = condition;
    }
    
    public void setConditionFromString(String conditionValue) {
        this.condition = DynamicEnum.fromString(CONDITION_ENUM_TYPE, conditionValue);
    }
    
    public static String getConditionEnumType() {
        return CONDITION_ENUM_TYPE;
    }
    
    public DynamicEnum getTemperatureSensation() {
        return temperatureSensation;
    }

    public void setTemperatureSensation(DynamicEnum temperatureSensation) {
        // Validate that the enum type is correct
        if (temperatureSensation != null && !TEMPERATURE_SENSATION_ENUM_TYPE.equals(temperatureSensation.getEnumType())) {
            throw new IllegalArgumentException("Expected enum type: " + TEMPERATURE_SENSATION_ENUM_TYPE + 
                                             ", but got: " + temperatureSensation.getEnumType());
        }
        this.temperatureSensation = temperatureSensation;
    }
    
    public void setTemperatureSensationFromString(String sensationValue) {
        this.temperatureSensation = DynamicEnum.fromString(TEMPERATURE_SENSATION_ENUM_TYPE, sensationValue);
    }
    
    public static String getTemperatureSensationEnumType() {
        return TEMPERATURE_SENSATION_ENUM_TYPE;
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