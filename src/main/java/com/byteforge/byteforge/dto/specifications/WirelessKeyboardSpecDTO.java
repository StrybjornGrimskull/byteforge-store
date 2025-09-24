package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WirelessKeyboardSpecDTO(
        @NotBlank(message = "Layout is required")
        String layout,
        
        @NotBlank(message = "Switch type is required")
        String switchType,
        
        @NotBlank(message = "Switch brand is required")
        String switchBrand,
        
        @NotBlank(message = "Switch model is required")
        String switchModel,
        
        @NotBlank(message = "Wireless technology is required")
        String wirelessTech,
        
        Boolean rgbLighting,
        
        Boolean hotSwappable,
        
        @NotNull(message = "Actuation force is required")
        @DecimalMin(value = "0.1", message = "Actuation force must be at least 0.1")
        BigDecimal actuationForce,
        
        @NotNull(message = "Travel distance is required")
        @DecimalMin(value = "0.1", message = "Travel distance must be at least 0.1")
        BigDecimal travelDistance,
        
        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be at least 1")
        Integer weight,
        
        @NotNull(message = "Battery life is required")
        @Min(value = 1, message = "Battery life must be at least 1")
        Integer batteryLife,
        
        @NotBlank(message = "Charging type is required")
        String chargingType,
        
        Boolean multiDevicePairing
) {}
