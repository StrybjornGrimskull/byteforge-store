package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.WirelessKeyboardSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.WirelessKeyboardSpec;
import com.byteforge.byteforge.repositories.WirelessKeyboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public void createWirelessKeyboardSpec(Product product, ProductCreateRequestDto request) {
        if (request.wirelessKeyboardSpec() == null) return;
        
        WirelessKeyboardSpec spec = new WirelessKeyboardSpec();
        spec.setProduct(product);
        spec.setLayout(request.wirelessKeyboardSpec().layout());
        spec.setSwitchType(request.wirelessKeyboardSpec().switchType());
        spec.setSwitchBrand(request.wirelessKeyboardSpec().switchBrand());
        spec.setSwitchModel(request.wirelessKeyboardSpec().switchModel());
        spec.setWirelessTech(request.wirelessKeyboardSpec().wirelessTech());
        spec.setRgbLighting(Boolean.parseBoolean(request.wirelessKeyboardSpec().rgbLighting()));
        spec.setHotSwappable(Boolean.parseBoolean(request.wirelessKeyboardSpec().hotSwappable()));
        spec.setActuationForce(parseBigDecimal(request.wirelessKeyboardSpec().actuationForce()));
        spec.setTravelDistance(parseBigDecimal(request.wirelessKeyboardSpec().travelDistance()));
        spec.setWeight(parseInt(request.wirelessKeyboardSpec().weight()));
        spec.setBatteryLife(parseInt(request.wirelessKeyboardSpec().batteryLife()));
        spec.setChargingType(request.wirelessKeyboardSpec().chargingType());
        spec.setMultiDevicePairing(Boolean.parseBoolean(request.wirelessKeyboardSpec().multiDevicePairing()));
        
        wirelessKeyboardSpecRepository.save(spec);
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
} 