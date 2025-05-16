package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.WirelessKeyboardSpec;
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
) implements ProductSpecDTO {

    public static WirelessKeyboardSpecDTO fromEntity(WirelessKeyboardSpec spec) {
        return new WirelessKeyboardSpecDTO(
                spec.getLayout(),
                spec.getSwitchType(),
                spec.getSwitchBrand(),
                spec.getSwitchModel(),
                spec.getWirelessTech(),
                spec.getRgbLighting(),
                spec.getHotSwappable(),
                spec.getActuationForce(),
                spec.getTravelDistance(),
                spec.getWeight(),
                spec.getBatteryLife(),
                spec.getChargingType(),
                spec.getMultiDevicePairing()
        );
    }
}
