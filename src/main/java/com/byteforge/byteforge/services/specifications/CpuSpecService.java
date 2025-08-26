package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.CpuSpecDTO;
import com.byteforge.byteforge.repositories.CpuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpuSpecService {

    private final CpuSpecRepository cpuSpecRepository;

    public CpuSpecDTO getCpuSpecByProductId(Integer productId) {
        return cpuSpecRepository.findByProductId(productId)
                .map(spec -> new CpuSpecDTO(
                        spec.getCores(),
                        spec.getThreads(),
                        spec.getBaseClock(),
                        spec.getBoostClock(),
                        spec.getSocket(),
                        spec.getCacheSize(),
                        spec.getTdp(),
                        spec.getIntegratedGpu()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
}