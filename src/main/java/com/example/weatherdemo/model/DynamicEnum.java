package com.example.weatherdemo.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dynamic enum representation", type = "string")
@JsonFormat(shape = JsonFormat.Shape.STRING)
public class DynamicEnum {
    private final String enumType;
    private final String name;
    private final int value;
    private final String displayName;
    private final String description;
    
    // Static reference to the factory for JSON deserialization
    private static DynamicEnumFactory factory;
    
    public static void setFactory(DynamicEnumFactory factory) {
        DynamicEnum.factory = factory;
    }
    
    private DynamicEnum(String enumType, String name, int value, String displayName, String description) {
        this.enumType = enumType;
        this.name = name;
        this.value = value;
        this.displayName = displayName;
        this.description = description;
    }
    
    /**
     * Creates a DynamicEnum instance from an EnumEntry
     */
    public static DynamicEnum from(EnumEntry entry) {
        if (entry == null) {
            return null;
        }
        return new DynamicEnum(
            entry.getEnumType(),
            entry.getName(), 
            entry.getValue(), 
            entry.getDisplayName(), 
            entry.getDescription()
        );
    }
    
    /**
     * Gets the enum type
     */
    public String getEnumType() {
        return enumType;
    }
    
    /**
     * Gets the enum name
     */
    public String name() {
        return name;
    }
    
    /**
     * Gets the numeric value of this enum
     */
    @JsonValue
    public int getValue() {
        return value;
    }

    /**
     * Gets the display name of this enum
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Gets the description of this enum
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Deserialize from string (enum name or numeric value) for a specific enum type
     */
    @JsonCreator
    public static DynamicEnum fromString(String enumType, String value) {
        if (factory == null) {
            throw new IllegalStateException("DynamicEnumFactory not initialized");
        }
        return factory.fromString(enumType, value);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DynamicEnum that = (DynamicEnum) o;
        return value == that.value && 
               Objects.equals(enumType, that.enumType) &&
               Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(enumType, name, value);
    }
}