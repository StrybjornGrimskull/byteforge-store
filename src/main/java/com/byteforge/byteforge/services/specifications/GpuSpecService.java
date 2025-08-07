package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.GpuSpecDTO;
import com.byteforge.byteforge.repositories.GpuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GpuSpecService {

    private final GpuSpecRepository gpuSpecRepository;

    public GpuSpecDTO getGpuSpecByProductId(Integer productId) {
        return gpuSpecRepository.findByProductId(productId)
                .map(GpuSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 