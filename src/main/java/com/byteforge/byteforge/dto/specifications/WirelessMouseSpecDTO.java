package com.byteforge.byteforge.dto.specifications;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WirelessMouseSpecDTO(
        @NotBlank(message = "Sensor type is required")
        String sensorType,
        
        @NotBlank(message = "Sensor model is required")
        String sensorModel,
        
        @NotNull(message = "Max DPI is required")
        @Min(value = 1, message = "Max DPI must be at least 1")
        Integer maxDpi,
        
        @NotNull(message = "Buttons is required")
        @Min(value = 1, message = "Buttons must be at least 1")
        Integer buttons,
        
        @NotBlank(message = "Wireless technology is required")
        String wirelessTech,
        
        @NotNull(message = "Polling rate is required")
        @Min(value = 1, message = "Polling rate must be at least 1")
        Integer pollingRate,
        
        @NotNull(message = "Weight is required")
        @Min(value = 1, message = "Weight must be at least 1")
        Integer weight,
        
        Boolean rgbLighting,
        
        @NotBlank(message = "Battery type is required")
        String batteryType,
        
        @NotNull(message = "Battery life is required")
        @Min(value = 1, message = "Battery life must be at least 1")
        Integer batteryLife,
        
        @NotNull(message = "Standby battery life is required")
        @Min(value = 1, message = "Standby battery life must be at least 1")
        Integer standbyBatteryLife,
        
        @NotNull(message = "Charging time is required")
        @Min(value = 1, message = "Charging time must be at least 1")
        Integer chargingTime,
        
        Boolean onboardMemory
) {}
