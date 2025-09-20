package com.byteforge.byteforge.dto.request.specifications;

public record WiredMouseSpecRequestDto(
        String sensorType,
        String sensorModel,
        String maxDpi,
        String adjustableDpi,
        String buttons,
        String cableType,
        String usbConnector,
        String rgbLightingMouse,
        String onboardMemory
) {}
