package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.MonitorSpecDTO;
import com.byteforge.byteforge.repositories.MonitorSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorSpecService {

    private final MonitorSpecRepository monitorSpecRepository;

    public MonitorSpecDTO getMonitorSpecByProductId(Integer productId) {
        return monitorSpecRepository.findByProductId(productId)
                .map(spec -> new MonitorSpecDTO(
                        spec.getScreenSize(),
                        spec.getResolution(),
                        spec.getPanelType(),
                        spec.getRefreshRate(),
                        spec.getResponseTime()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 