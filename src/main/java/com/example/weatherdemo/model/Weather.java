package com.example.weatherdemo.model;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

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
    @EnumType("weatherType")
    private DynamicEnum condition;
    
    @Schema(
        description = "Temperature sensation", 
        example = "MILD", 
        type = "string",
        ref = "#/components/schemas/temperatureSensationEnum"
    )
    @EnumType("temperatureSensation")
    private DynamicEnum temperatureSensation;
    
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

    @JsonSetter("condition")
    public void setCondition(Object conditionObj) {
        if (conditionObj == null) {
            this.condition = null;
            return;
        }
        
        String conditionStr = conditionObj.toString();
        this.condition = DynamicEnum.fromString(getEnumTypeForField("condition"), conditionStr);
    }
    
    public void setCondition(DynamicEnum condition) {
        // Validate that the enum type is correct
        String expectedEnumType = getEnumTypeForField("condition");
        if (condition != null && !expectedEnumType.equals(condition.getEnumType())) {
            throw new IllegalArgumentException("Expected enum type: " + expectedEnumType + 
                                             ", but got: " + condition.getEnumType());
        }
        this.condition = condition;
    }
    
    public void setConditionFromString(String conditionValue) {
        this.condition = DynamicEnum.fromString(getEnumTypeForField("condition"), conditionValue);
    }
    
    public static String getConditionEnumType() {
        return getEnumTypeForField("condition");
    }
    
    public DynamicEnum getTemperatureSensation() {
        return temperatureSensation;
    }

    @JsonSetter("temperatureSensation")
    public void setTemperatureSensation(Object sensationObj) {
        if (sensationObj == null) {
            this.temperatureSensation = null;
            return;
        }
        
        String sensationStr = sensationObj.toString();
        this.temperatureSensation = DynamicEnum.fromString(getEnumTypeForField("temperatureSensation"), sensationStr);
    }
    
    public void setTemperatureSensation(DynamicEnum temperatureSensation) {
        // Validate that the enum type is correct
        String expectedEnumType = getEnumTypeForField("temperatureSensation");
        if (temperatureSensation != null && !expectedEnumType.equals(temperatureSensation.getEnumType())) {
            throw new IllegalArgumentException("Expected enum type: " + expectedEnumType + 
                                             ", but got: " + temperatureSensation.getEnumType());
        }
        this.temperatureSensation = temperatureSensation;
    }
    
    public void setTemperatureSensationFromString(String sensationValue) {
        this.temperatureSensation = DynamicEnum.fromString(getEnumTypeForField("temperatureSensation"), sensationValue);
    }
    
    public static String getTemperatureSensationEnumType() {
        return getEnumTypeForField("temperatureSensation");
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
    
    /**
     * Utility method to get the enum type from a field's EnumType annotation
     */
    private static String getEnumTypeForField(String fieldName) {
        try {
            Field field = Weather.class.getDeclaredField(fieldName);
            EnumType annotation = field.getAnnotation(EnumType.class);
            if (annotation != null) {
                return annotation.value();
            }
            throw new IllegalStateException("Field " + fieldName + " does not have an EnumType annotation");
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field not found: " + fieldName, e);
        }
    }
}