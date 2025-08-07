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
                .map(MotherboardSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 