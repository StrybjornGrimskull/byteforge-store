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
                .map(WiredMouseSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 