package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WiredKeyboardSpecDTO;
import com.byteforge.byteforge.repositories.WiredKeyboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
} 