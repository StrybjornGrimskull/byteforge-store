package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.WiredMouseSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.WiredMouseSpec;
import com.byteforge.byteforge.repositories.WiredMouseSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WiredMouseSpecService {

    private final WiredMouseSpecRepository wiredMouseSpecRepository;

    public WiredMouseSpecDTO getWiredMouseSpecByProductId(Integer productId) {
        return wiredMouseSpecRepository.findByProductId(productId)
                .map(spec -> new WiredMouseSpecDTO(
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
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createWiredMouseSpec(Product product, ProductCreateRequestDto request) {
        if (request.wiredMouseSpec() == null) return;
        
        WiredMouseSpec spec = new WiredMouseSpec();
        spec.setProduct(product);
        spec.setSensorType(request.wiredMouseSpec().sensorType());
        spec.setSensorModel(request.wiredMouseSpec().sensorModel());
        spec.setMaxDpi(parseInt(request.wiredMouseSpec().maxDpi()));
        spec.setAdjustableDpi(Boolean.parseBoolean(request.wiredMouseSpec().adjustableDpi()));
        spec.setButtons(parseInt(request.wiredMouseSpec().buttons()));
        spec.setCableLength(parseInt(request.wiredMouseSpec().cableType()));
        spec.setCableType(request.wiredMouseSpec().cableType());
        spec.setUsbConnector(request.wiredMouseSpec().usbConnector());
        spec.setWeight(parseInt(request.wiredMouseSpec().rgbLightingMouse()));
        spec.setRgbLighting(Boolean.parseBoolean(request.wiredMouseSpec().rgbLightingMouse()));
        spec.setOnboardMemory(Boolean.parseBoolean(request.wiredMouseSpec().onboardMemory()));
        spec.setWarrantyMonths(parseInt(request.wiredMouseSpec().onboardMemory()));
        
        wiredMouseSpecRepository.save(spec);
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