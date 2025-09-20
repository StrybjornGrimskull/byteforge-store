package com.byteforge.byteforge.dto.request.specifications;

public record WirelessKeyboardSpecRequestDto(
        String layout,
        String switchType,
        String switchBrand,
        String switchModel,
        String wirelessTech,
        String rgbLighting,
        String hotSwappable,
        String actuationForce,
        String travelDistance,
        String weight,
        String batteryLife,
        String chargingType,
        String multiDevicePairing
) {}
