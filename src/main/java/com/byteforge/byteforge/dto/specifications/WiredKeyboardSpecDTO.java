package com.byteforge.byteforge.dto.specifications;

import java.math.BigDecimal;

public record WiredKeyboardSpecDTO(
        String layout,
        String switchType,
        String switchBrand,
        String switchModel,
        Boolean rgbLighting,
        Boolean hotSwappable,
        BigDecimal actuationForce,
        BigDecimal travelDistance,
        Integer weight,
        BigDecimal cableLength,
        Boolean usbPassthrough,
        Boolean detachableCable
) {}
