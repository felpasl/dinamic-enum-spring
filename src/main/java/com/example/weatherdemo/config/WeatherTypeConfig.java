package com.example.weatherdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.example.weatherdemo.model.DynamicEnum;
import com.example.weatherdemo.model.DynamicEnumFactory;

@Configuration
public class WeatherTypeConfig {

    /**
     * Bean to initialize the static factory reference in DynamicEnum
     */
    @Bean
    @DependsOn("dynamicEnumFactory")
    public Boolean initDynamicEnum(DynamicEnumFactory factory) {
        DynamicEnum.setFactory(factory);
        return true;
    }
}