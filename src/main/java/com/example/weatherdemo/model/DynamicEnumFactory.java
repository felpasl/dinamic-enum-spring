package com.example.weatherdemo.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.weatherdemo.service.DynamicEnumService;

/**
 * Factory for creating and caching DynamicEnum instances from dynamic enum values
 */
@Component
public class DynamicEnumFactory {
    
    private final DynamicEnumService dynamicEnumService;
    private final Map<String, Map<String, DynamicEnum>> valuesByName = new ConcurrentHashMap<>();
    private final Map<String, Map<Integer, DynamicEnum>> valuesByCode = new ConcurrentHashMap<>();
    
    @Autowired
    public DynamicEnumFactory(DynamicEnumService dynamicEnumService) {
        this.dynamicEnumService = dynamicEnumService;
        DynamicEnum.setFactory(this);
    }
    
    /**
     * Get all available enum values of a specific type
     */
    public List<DynamicEnum> getAllByType(String enumType) {
        return dynamicEnumService.getEnumsByType(enumType)
                .stream()
                .map(DynamicEnum::from)
                .collect(Collectors.toList());
    }
    
    /**
     * Get an enum value by its type and name
     */
    public DynamicEnum getByName(String enumType, String name) {
        Map<String, DynamicEnum> typeMap = valuesByName.computeIfAbsent(enumType, k -> new ConcurrentHashMap<>());
        
        DynamicEnum result = typeMap.get(name);
        if (result == null) {
            EnumEntry entry = dynamicEnumService.getEnumByName(enumType, name);
            if (entry != null) {
                result = DynamicEnum.from(entry);
                typeMap.put(name, result);
                
                Map<Integer, DynamicEnum> codeMap = valuesByCode.computeIfAbsent(enumType, k -> new ConcurrentHashMap<>());
                codeMap.put(entry.getValue(), result);
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Unknown enum name: " + name + " for type: " + enumType);
        }
        return result;
    }
    
    /**
     * Get an enum value by its type and code
     */
    public DynamicEnum getByValue(String enumType, int value) {
        Map<Integer, DynamicEnum> typeMap = valuesByCode.computeIfAbsent(enumType, k -> new ConcurrentHashMap<>());
        
        DynamicEnum result = typeMap.get(value);
        if (result == null) {
            EnumEntry entry = dynamicEnumService.getEnumByValue(enumType, value);
            if (entry != null) {
                result = DynamicEnum.from(entry);
                typeMap.put(value, result);
                
                Map<String, DynamicEnum> nameMap = valuesByName.computeIfAbsent(enumType, k -> new ConcurrentHashMap<>());
                nameMap.put(entry.getName(), result);
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Unknown enum value: " + value + " for type: " + enumType);
        }
        return result;
    }
    
    /**
     * Parse from string (either name or numeric value) for a specific enum type
     */
    public DynamicEnum fromString(String enumType, String value) {
        try {
            int numericValue = Integer.parseInt(value);
            return getByValue(enumType, numericValue);
        } catch (NumberFormatException e) {
            return getByName(enumType, value);
        }
    }
}