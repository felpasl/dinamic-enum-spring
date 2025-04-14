package com.example.weatherdemo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.weatherdemo.model.EnumEntry;

@Service
public class DynamicEnumService {

    @Value("classpath:enum.csv")
    private Resource enumCsvResource;

    private final Map<String, List<EnumEntry>> enumsByType = new HashMap<>();
    private final Map<String, Map<String, EnumEntry>> enumsByName = new HashMap<>();
    private final Map<String, Map<Integer, EnumEntry>> enumsByValue = new HashMap<>();

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(enumCsvResource.getInputStream()))) {
            // Skip header line
            String header = reader.readLine();
            if (header == null || !header.startsWith("enum,")) {
                throw new IllegalStateException("Invalid enum.csv format, expected header starting with 'enum,'");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 5) {
                    String enumType = parts[0].trim();
                    String name = parts[1].trim();
                    Integer value = Integer.parseInt(parts[2].trim());
                    String displayName = parts[3].trim();
                    String description = parts[4].trim();

                    EnumEntry entry = new EnumEntry(enumType, name, value, displayName, description);
                    
                    // Store by enum type
                    enumsByType.computeIfAbsent(enumType, k -> new ArrayList<>()).add(entry);
                    
                    // Store by enum name
                    enumsByName.computeIfAbsent(enumType, k -> new HashMap<>()).put(name, entry);
                    
                    // Store by enum value
                    enumsByValue.computeIfAbsent(enumType, k -> new HashMap<>()).put(value, entry);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load enum definitions from CSV", e);
        }
    }

    /**
     * Get all available enum types
     */
    public Set<String> getAllEnumTypes() {
        return enumsByType.keySet();
    }

    /**
     * Get all entries of a specific enum type, or all entries if type is empty
     */
    public List<EnumEntry> getEnumsByType(String enumType) {
        if (enumType == null || enumType.isEmpty()) {
            return enumsByType.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        }
        return enumsByType.getOrDefault(enumType, List.of());
    }

    public EnumEntry getEnumByName(String enumType, String name) {
        Map<String, EnumEntry> nameMap = enumsByName.get(enumType);
        return nameMap != null ? nameMap.get(name) : null;
    }

    public EnumEntry getEnumByValue(String enumType, Integer value) {
        Map<Integer, EnumEntry> valueMap = enumsByValue.get(enumType);
        return valueMap != null ? valueMap.get(value) : null;
    }
    
    public List<String> getDisplayNames(String enumType) {
        return getEnumsByType(enumType).stream()
                .map(EnumEntry::getDisplayName)
                .collect(Collectors.toList());
    }
    
    public Map<String, Integer> getNameValueMap(String enumType) {
        return getEnumsByType(enumType).stream()
                .collect(Collectors.toMap(EnumEntry::getName, EnumEntry::getValue));
    }
    
    public Map<String, String> getValueDescriptionMap(String enumType) {
        return getEnumsByType(enumType).stream()
                .collect(Collectors.toMap(
                    entry -> entry.getValue().toString(), 
                    EnumEntry::getDescription
                ));
    }
}