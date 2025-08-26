package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.PsuSpecDTO;
import com.byteforge.byteforge.repositories.PsuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PsuSpecService {

    private final PsuSpecRepository psuSpecRepository;

    public PsuSpecDTO getPsuSpecByProductId(Integer productId) {
        return psuSpecRepository.findByProductId(productId)
                .map(spec -> new PsuSpecDTO(
                        spec.getWattage(),
                        spec.getFormFactor(),
                        spec.getEfficiencyCert(),
                        spec.getModularity(),
                        spec.getPcie8pinConnectors(),
                        spec.getSataConnectors()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 