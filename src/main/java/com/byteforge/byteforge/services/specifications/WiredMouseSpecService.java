package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WiredMouseSpecDTO;
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
} 