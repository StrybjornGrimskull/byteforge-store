package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessMouseSpecDTO;
import com.byteforge.byteforge.repositories.WirelessMouseSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WirelessMouseSpecService {

    private final WirelessMouseSpecRepository wirelessMouseSpecRepository;

    public WirelessMouseSpecDTO getWirelessMouseSpecByProductId(Integer productId) {
        return wirelessMouseSpecRepository.findByProductId(productId)
                .map(spec -> new WirelessMouseSpecDTO(
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
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 