package com.example.weatherdemo.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.weatherdemo.model.EnumEntry;
import com.example.weatherdemo.service.DynamicEnumService;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    private final DynamicEnumService dynamicEnumService;

    public OpenApiConfig(DynamicEnumService dynamicEnumService) {
        this.dynamicEnumService = dynamicEnumService;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("Weather Demo API")
                        .version("1.0.0")
                        .description("API for retrieving weather data")
                        .contact(new Contact()
                                .name("Weather Team")
                                .email("support@weatherdemo.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .addServersItem(new Server().url("/").description("Default Server URL"));

        // Create components section if not exists
        final Components components = openAPI.getComponents() != null ? 
                                     openAPI.getComponents() : new Components();
        openAPI.setComponents(components);

        // Get all enum entries and group by enum type
        Map<String, List<EnumEntry>> entriesByType = dynamicEnumService.getEnumsByType("").stream()
                .collect(Collectors.groupingBy(EnumEntry::getEnumType));
        
        // Add schemas for each enum type
        entriesByType.forEach((enumType, entries) -> {
            // Create a proper enum schema instead of string schema with extensions
            StringSchema schema = new StringSchema();
            
            // Add the enum values directly using setEnum
            List<String> enumValues = entries.stream()
                    .map(EnumEntry::getName)
                    .collect(Collectors.toList());
            
            // This properly sets the enum values in the schema
            schema.setEnum(enumValues);
            
            // Add description for the enum type
            StringBuilder description = new StringBuilder("Available values for " + enumType + ":");
            entries.forEach(entry -> {
                description.append("\n- ")
                          .append(entry.getName())
                          .append(": ")
                          .append(entry.getDescription());
            });
            schema.description(description.toString());
            
            // Register schema
            components.addSchemas(enumType + "Enum", schema);
        });

        return openAPI;
    }
}