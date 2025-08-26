package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.SsdSpecDTO;
import com.byteforge.byteforge.repositories.SsdSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SsdSpecService {

    private final SsdSpecRepository ssdSpecRepository;

    public SsdSpecDTO getSsdSpecByProductId(Integer productId) {
        return ssdSpecRepository.findByProductId(productId)
                .map(spec -> new SsdSpecDTO(
                        spec.getCapacity(),
                        spec.getFormFactor(),
                        spec.getInterfaceType(),
                        spec.getReadSpeed(),
                        spec.getWriteSpeed(),
                        spec.getMemoryType(),
                        spec.getEnduranceTbw(),
                        spec.getDramCache(),
                        spec.getEncryption(),
                        spec.getThickness()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 