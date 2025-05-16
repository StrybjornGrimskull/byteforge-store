package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.WiredKeyboardSpec;
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
) implements ProductSpecDTO {

    public static WiredKeyboardSpecDTO fromEntity(WiredKeyboardSpec spec) {
        return new WiredKeyboardSpecDTO(
                spec.getLayout(),
                spec.getSwitchType(),
                spec.getSwitchBrand(),
                spec.getSwitchModel(),
                spec.getRgbLighting(),
                spec.getHotSwappable(),
                spec.getActuationForce(),
                spec.getTravelDistance(),
                spec.getWeight(),
                spec.getCableLength(),
                spec.getUsbPassthrough(),
                spec.getDetachableCable()
        );
    }
}
