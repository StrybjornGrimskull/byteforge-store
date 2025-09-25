package com.byteforge.byteforge.dto.request.specifications;

import jakarta.validation.constraints.NotBlank;

public record WirelessKeyboardSpecRequestDto(
        @NotBlank(message = "Layout cannot be empty") String layout,
        @NotBlank(message = "Switch type cannot be empty") String switchType,
        @NotBlank(message = "Switch brand cannot be empty") String switchBrand,
        @NotBlank(message = "Switch model cannot be empty") String switchModel,
        @NotBlank(message = "Wireless technology cannot be empty") String wirelessTech,
        @NotBlank(message = "RGB lighting cannot be empty") String rgbLighting,
        @NotBlank(message = "Hot swappable cannot be empty") String hotSwappable,
        @NotBlank(message = "Actuation force cannot be empty") String actuationForce,
        @NotBlank(message = "Travel distance cannot be empty") String travelDistance,
        @NotBlank(message = "Weight cannot be empty") String weight,
        @NotBlank(message = "Battery life cannot be empty") String batteryLife,
        @NotBlank(message = "Charging type cannot be empty") String chargingType,
        @NotBlank(message = "Multi-device pairing cannot be empty") String multiDevicePairing
) {}
