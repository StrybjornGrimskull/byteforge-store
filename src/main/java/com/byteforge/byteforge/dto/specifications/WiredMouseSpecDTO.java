package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WiredMouseSpecDTO(
        @NotBlank(message = "Sensor type is required")
        String sensorType,
        
        @NotBlank(message = "Sensor model is required")
        String sensorModel,
        
        @NotNull(message = "Max DPI is required")
        @Min(value = 1, message = "Max DPI must be at least 1")
        Integer maxDpi,
        
        Boolean adjustableDpi,
        
        @NotNull(message = "Buttons is required")
        @Min(value = 1, message = "Buttons must be at least 1")
        Integer buttons,
        
        @NotNull(message = "Cable length is required")
        @Min(value = 1, message = "Cable length must be at least 1")
        Integer cableLength,
        
        @NotBlank(message = "Cable type is required")
        String cableType,
        
        @NotBlank(message = "USB connector is required")
        String usbConnector,
        
        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be at least 1")
        Integer weight,
        
        Boolean rgbLighting,
        
        Boolean onboardMemory
) {}
