package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record WirelessMouseSpecRequestDto(
        @NotBlank(message = "Sensor type cannot be empty") String sensorType,
        @NotBlank(message = "Sensor model cannot be empty") String sensorModel,
        @NotBlank(message = "Max DPI cannot be empty") String maxDpi,
        @NotBlank(message = "Buttons cannot be empty") String buttons,
        @NotBlank(message = "Wireless technology cannot be empty") String wirelessTech,
        @NotBlank(message = "Polling rate cannot be empty") String pollingRate,
        @NotBlank(message = "Weight cannot be empty") String weight,
        String rgbLighting,
        @NotBlank(message = "Battery type cannot be empty") String batteryType,
        @NotBlank(message = "Battery life cannot be empty") String batteryLife,
        @NotBlank(message = "Standby battery life cannot be empty") String standbyBatteryLife,
        @NotBlank(message = "Charging time cannot be empty") String chargingTime,
        String onboardMemory
) {}
