package com.example.weatherdemo.model;

/**
 * Represents a single entry in an enum definition loaded from a CSV file.
 */
public class EnumEntry {
    private String enumType;
    private String name;
    private Integer value;
    private String displayName;
    private String description;

    public EnumEntry() {
    }

    public EnumEntry(String enumType, String name, Integer value, String displayName, String description) {
        this.enumType = enumType;
        this.name = name;
        this.value = value;
        this.displayName = displayName;
        this.description = description;
    }

    public String getEnumType() {
        return enumType;
    }

    public void setEnumType(String enumType) {
        this.enumType = enumType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EnumEntry{" +
                "enumType='" + enumType + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}