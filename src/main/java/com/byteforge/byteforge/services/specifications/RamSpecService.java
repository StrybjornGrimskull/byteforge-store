package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.RamSpecDTO;
import com.byteforge.byteforge.repositories.RamSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RamSpecService {

    private final RamSpecRepository ramSpecRepository;

    public RamSpecDTO getRamSpecByProductId(Integer productId) {
        return ramSpecRepository.findByProductId(productId)
                .map(spec -> new RamSpecDTO(
                        spec.getMemorySize(),
                        spec.getModulesCount(),
                        spec.getSpeed(),
                        spec.getType(),
                        spec.getTimings(),
                        spec.getVoltage()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 