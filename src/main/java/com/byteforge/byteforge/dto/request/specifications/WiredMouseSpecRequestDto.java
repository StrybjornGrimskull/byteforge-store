package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record WiredMouseSpecRequestDto(
        @NotBlank(message = "Sensor type cannot be empty") String sensorType,
        @NotBlank(message = "Sensor model cannot be empty") String sensorModel,
        @NotBlank(message = "Max DPI cannot be empty") String maxDpi,
        String adjustableDpi,
        @NotBlank(message = "Buttons cannot be empty") String buttons,
        @NotBlank(message = "Cable length cannot be empty") String cableLength,
        @NotBlank(message = "Cable type cannot be empty") String cableType,
        @NotBlank(message = "USB connector cannot be empty") String usbConnector,
        @NotBlank(message = "Weight cannot be empty") String weight,
        String rgbLighting,
        String onboardMemory
) {}
