package com.example.weatherdemo.model;

public enum WeatherType {
    SUNNY("Sunny"),
    PARTLY_CLOUDY("Partly Cloudy"),
    CLOUDY("Cloudy"),
    RAINY("Rainy"),
    THUNDERSTORM("Thunderstorm"),
    SNOWY("Snowy"),
    FOGGY("Foggy"),
    WINDY("Windy");

    private final String displayName;

    WeatherType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}