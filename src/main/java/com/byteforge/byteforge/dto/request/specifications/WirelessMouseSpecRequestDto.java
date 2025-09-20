package com.byteforge.byteforge.dto.request.specifications;

public record WirelessMouseSpecRequestDto(
        String sensorType,
        String sensorModel,
        String maxDpi,
        String buttons,
        String wirelessTech,
        String pollingRate,
        String weight,
        String rgbLightingMouse,
        String batteryType,
        String batteryLife,
        String standbyBatteryLife,
        String chargingTime,
        String onboardMemory
) {}
