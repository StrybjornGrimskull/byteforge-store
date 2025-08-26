package com.byteforge.byteforge.dto.specifications;

public record WiredMouseSpecDTO(
        String sensorType,
        String sensorModel,
        Integer maxDpi,
        Boolean adjustableDpi,
        Integer buttons,
        Integer cableLength,
        String cableType,
        String usbConnector,
        Integer weight,
        Boolean rgbLighting,
        Boolean onboardMemory,
        Integer warrantyMonths
) {}
