package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WiredKeyboardSpecDTO(
        @NotBlank(message = "Layout is required")
        String layout,
        
        @NotBlank(message = "Switch type is required")
        String switchType,
        
        @NotBlank(message = "Switch brand is required")
        String switchBrand,
        
        @NotBlank(message = "Switch model is required")
        String switchModel,
        
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
        
        @NotNull(message = "Cable length is required")
        @DecimalMin(value = "0.1", message = "Cable length must be at least 0.1")
        BigDecimal cableLength,
        
        Boolean usbPassthrough,
        
        Boolean detachableCable
) {}
