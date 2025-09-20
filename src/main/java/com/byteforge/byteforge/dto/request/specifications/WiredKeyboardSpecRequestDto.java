package com.byteforge.byteforge.dto.request.specifications;

public record WiredKeyboardSpecRequestDto(
        String layout,
        String switchType,
        String switchBrand,
        String switchModel,
        String rgbLighting,
        String hotSwappable,
        String actuationForce,
        String travelDistance,
        String weight,
        String cableLength,
        String usbPassthrough,
        String detachableCable
) {}
