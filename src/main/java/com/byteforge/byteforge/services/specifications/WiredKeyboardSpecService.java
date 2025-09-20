package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.WiredKeyboardSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.WiredKeyboardSpec;
import com.byteforge.byteforge.repositories.WiredKeyboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WiredKeyboardSpecService {

    private final WiredKeyboardSpecRepository wiredKeyboardSpecRepository;

    public WiredKeyboardSpecDTO getWiredKeyboardSpecByProductId(Integer productId) {
        return wiredKeyboardSpecRepository.findByProductId(productId)
                .map(spec -> new WiredKeyboardSpecDTO(
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
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createWiredKeyboardSpec(Product product, ProductCreateRequestDto request) {
        if (request.wiredKeyboardSpec() == null) return;
        
        WiredKeyboardSpec spec = new WiredKeyboardSpec();
        spec.setProduct(product);
        spec.setLayout(request.wiredKeyboardSpec().layout());
        spec.setSwitchType(request.wiredKeyboardSpec().switchType());
        spec.setSwitchBrand(request.wiredKeyboardSpec().switchBrand());
        spec.setSwitchModel(request.wiredKeyboardSpec().switchModel());
        spec.setRgbLighting(Boolean.parseBoolean(request.wiredKeyboardSpec().rgbLighting()));
        spec.setHotSwappable(Boolean.parseBoolean(request.wiredKeyboardSpec().hotSwappable()));
        spec.setActuationForce(parseBigDecimal(request.wiredKeyboardSpec().actuationForce()));
        spec.setTravelDistance(parseBigDecimal(request.wiredKeyboardSpec().travelDistance()));
        spec.setWeight(parseInt(request.wiredKeyboardSpec().weight()));
        spec.setCableLength(parseBigDecimal(request.wiredKeyboardSpec().cableLength()));
        spec.setUsbPassthrough(Boolean.parseBoolean(request.wiredKeyboardSpec().usbPassthrough()));
        spec.setDetachableCable(Boolean.parseBoolean(request.wiredKeyboardSpec().detachableCable()));
        
        wiredKeyboardSpecRepository.save(spec);
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