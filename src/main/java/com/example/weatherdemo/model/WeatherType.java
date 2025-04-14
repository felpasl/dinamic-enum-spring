package com.example.weatherdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of weather conditions")
public enum WeatherType {
    @Schema(description = "Clear sky with sun") SUNNY("Sunny"),
    @Schema(description = "Mix of sun and clouds") PARTLY_CLOUDY("Partly Cloudy"),
    @Schema(description = "Sky covered with clouds") CLOUDY("Cloudy"),
    @Schema(description = "Precipitation in the form of water drops") RAINY("Rainy"),
    @Schema(description = "Storm with lightning and thunder") THUNDERSTORM("Thunderstorm"),
    @Schema(description = "Precipitation in the form of snowflakes") SNOWY("Snowy"),
    @Schema(description = "Low hanging cloud reducing visibility") FOGGY("Foggy"),
    @Schema(description = "Strong air movement") WINDY("Windy");

    private final String displayName;

    WeatherType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}