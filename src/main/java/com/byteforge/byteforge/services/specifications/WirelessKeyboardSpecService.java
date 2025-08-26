package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessKeyboardSpecDTO;
import com.byteforge.byteforge.repositories.WirelessKeyboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WirelessKeyboardSpecService {

    private final WirelessKeyboardSpecRepository wirelessKeyboardSpecRepository;

    public WirelessKeyboardSpecDTO getWirelessKeyboardSpecByProductId(Integer productId) {
        return wirelessKeyboardSpecRepository.findByProductId(productId)
                .map(spec -> new WirelessKeyboardSpecDTO(
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
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 