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
                .map(PsuSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 