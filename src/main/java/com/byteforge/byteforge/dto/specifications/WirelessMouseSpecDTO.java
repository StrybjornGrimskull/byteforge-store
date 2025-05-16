package com.byteforge.byteforge.dto.specifications;

import com.byteforge.byteforge.entities.WirelessMouseSpec;

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
) implements ProductSpecDTO {

    public static WirelessMouseSpecDTO fromEntity(WirelessMouseSpec spec) {
        return new WirelessMouseSpecDTO(
                spec.getSensorType(),
                spec.getSensorModel(),
                spec.getMaxDpi(),
                spec.getButtons(),
                spec.getWirelessTech(),
                spec.getPollingRate(),
                spec.getWeight(),
                spec.getRgbLighting(),
                spec.getBatteryType(),
                spec.getBatteryLife(),
                spec.getStandbyBatteryLife(),
                spec.getChargingTime(),
                spec.getOnboardMemory(),
                spec.getWarrantyMonths()
        );
    }
}
