package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.MotherboardSpecDTO;
import com.byteforge.byteforge.repositories.MotherboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotherboardSpecService {

    private final MotherboardSpecRepository motherboardSpecRepository;

    public MotherboardSpecDTO getMotherboardSpecByProductId(Integer productId) {
        return motherboardSpecRepository.findByProductId(productId)
                .map(spec -> new MotherboardSpecDTO(
                        spec.getSocket(),
                        spec.getChipset(),
                        spec.getFormFactor(),
                        spec.getMemorySlots(),
                        spec.getMaxMemory(),
                        spec.getMemoryType(),
                        spec.getM2Slots(),
                        spec.getSataPorts()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 