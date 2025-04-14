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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Weather API endpoints")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(
        summary = "Get random weather data",
        description = "Returns random weather conditions for a random location",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Weather.class))
            )
        }
    )
    @GetMapping("/random")
    public ResponseEntity<Weather> getRandomWeather() {
        return ResponseEntity.ok(weatherService.getRandomWeather());
    }

    @Operation(
        summary = "Get random weather for specific location",
        description = "Returns random weather conditions for the specified location",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful operation",
                content = @Content(schema = @Schema(implementation = Weather.class))
            )
        }
    )
    @GetMapping("/random/{location}")
    public ResponseEntity<Weather> getRandomWeatherForLocation(
        @Parameter(description = "Location name") @PathVariable String location) {
        return ResponseEntity.ok(weatherService.getRandomWeather(location));
    }

    @Operation(
        summary = "Get multiple random weather data points",
        description = "Returns random weather conditions for multiple locations. Count between 1-20.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful operation",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = Weather.class)))
            )
        }
    )
    @GetMapping("/random/multiple/{count}")
    public ResponseEntity<List<Weather>> getMultipleRandomWeather(
        @Parameter(description = "Number of weather data points to return (1-20)") @PathVariable int count) {
        if (count < 1 || count > 20) {
            count = 5; // Default to 5 if out of reasonable range
        }
        return ResponseEntity.ok(weatherService.getRandomWeatherForMultipleLocations(count));
    }

    @Operation(
        summary = "Get all weather types",
        description = "Returns a list of all available weather types with their display names",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Successful operation",
                content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))
            )
        }
    )
    @GetMapping("/types")
    public ResponseEntity<List<String>> getWeatherTypes() {
        List<String> types = Arrays.stream(WeatherType.values())
                .map(WeatherType::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }
}