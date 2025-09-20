package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.WirelessMouseSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.WirelessMouseSpec;
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

    public void createWirelessMouseSpec(Product product, ProductCreateRequestDto request) {
        if (request.wirelessMouseSpec() == null) return;
        
        WirelessMouseSpec spec = new WirelessMouseSpec();
        spec.setProduct(product);
        spec.setSensorType(request.wirelessMouseSpec().sensorType());
        spec.setSensorModel(request.wirelessMouseSpec().sensorModel());
        spec.setMaxDpi(parseInt(request.wirelessMouseSpec().maxDpi()));
        spec.setButtons(parseInt(request.wirelessMouseSpec().buttons()));
        spec.setWirelessTech(request.wirelessMouseSpec().wirelessTech());
        spec.setPollingRate(parseInt(request.wirelessMouseSpec().pollingRate()));
        spec.setWeight(parseInt(request.wirelessMouseSpec().weight()));
        spec.setRgbLighting(Boolean.parseBoolean(request.wirelessMouseSpec().rgbLightingMouse()));
        spec.setBatteryType(request.wirelessMouseSpec().batteryType());
        spec.setBatteryLife(parseInt(request.wirelessMouseSpec().batteryLife()));
        spec.setStandbyBatteryLife(parseInt(request.wirelessMouseSpec().standbyBatteryLife()));
        spec.setChargingTime(parseInt(request.wirelessMouseSpec().chargingTime()));
        spec.setOnboardMemory(Boolean.parseBoolean(request.wirelessMouseSpec().onboardMemory()));
        spec.setWarrantyMonths(parseInt(request.wirelessMouseSpec().pollingRate()));
        
        wirelessMouseSpecRepository.save(spec);
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
} 