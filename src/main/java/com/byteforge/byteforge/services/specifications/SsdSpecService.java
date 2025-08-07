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
                .map(SsdSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 