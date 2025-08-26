package com.byteforge.byteforge.dto.specifications;

public record WirelessMouseSpecDTO(
        String sensorType,
        String sensorModel,
        Integer maxDpi,
        Integer buttons,
        String wirelessTech,
        Integer pollingRate,
        Integer weight,
        Boolean rgbLighting,
        String batteryType,
        Integer batteryLife,
        Integer standbyBatteryLife,
        Integer chargingTime,
        Boolean onboardMemory,
        Integer warrantyMonths
) {}
