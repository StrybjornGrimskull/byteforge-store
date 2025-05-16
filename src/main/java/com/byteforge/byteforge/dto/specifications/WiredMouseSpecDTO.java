package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.WiredMouseSpec;

public record WiredMouseSpecDTO(
        String sensorType,
        String sensorModel,
        Integer maxDpi,
        Boolean adjustableDpi,
        Integer buttons,
        Integer cableLength,
        String cableType,
        String usbConnector,
        Integer weight,
        Boolean rgbLighting,
        Boolean onboardMemory,
        Integer warrantyMonths
) implements ProductSpecDTO {

    public static WiredMouseSpecDTO fromEntity(WiredMouseSpec spec) {
        return new WiredMouseSpecDTO(
                spec.getSensorType(),
                spec.getSensorModel(),
                spec.getMaxDpi(),
                spec.getAdjustableDpi(),
                spec.getButtons(),
                spec.getCableLength(),
                spec.getCableType(),
                spec.getUsbConnector(),
                spec.getWeight(),
                spec.getRgbLighting(),
                spec.getOnboardMemory(),
                spec.getWarrantyMonths()
        );
    }
}
