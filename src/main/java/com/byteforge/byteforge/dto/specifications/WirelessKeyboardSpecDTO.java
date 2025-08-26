package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record WirelessKeyboardSpecDTO(
        String layout,
        String switchType,
        String switchBrand,
        String switchModel,
        String wirelessTech,
        Boolean rgbLighting,
        Boolean hotSwappable,
        BigDecimal actuationForce,
        BigDecimal travelDistance,
        Integer weight,
        Integer batteryLife,
        String chargingType,
        Boolean multiDevicePairing
) {}
