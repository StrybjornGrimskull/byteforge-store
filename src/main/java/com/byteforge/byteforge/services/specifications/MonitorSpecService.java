package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.MonitorSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.MonitorSpec;
import com.byteforge.byteforge.repositories.MonitorSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public void createMonitorSpec(Product product, ProductCreateRequestDto request) {
        if (request.monitorSpec() == null) return;
        
        MonitorSpec spec = new MonitorSpec();
        spec.setProduct(product);
        spec.setScreenSize(parseBigDecimal(request.monitorSpec().screenSize()));
        spec.setResolution(request.monitorSpec().resolution());
        spec.setPanelType(request.monitorSpec().panelType());
        spec.setRefreshRate(parseInt(request.monitorSpec().refreshRate()));
        spec.setResponseTime(parseInt(request.monitorSpec().responseTime()));
        
        monitorSpecRepository.save(spec);
    }

    public void updateMonitorSpec(Integer productId, MonitorSpecDTO dto) {
        MonitorSpec spec = monitorSpecRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
        if (dto.screenSize() != null) spec.setScreenSize(dto.screenSize());
        if (dto.resolution() != null) spec.setResolution(dto.resolution());
        if (dto.panelType() != null) spec.setPanelType(dto.panelType());
        if (dto.refreshRate() != null) spec.setRefreshRate(dto.refreshRate());
        if (dto.responseTime() != null) spec.setResponseTime(dto.responseTime());
        monitorSpecRepository.save(spec);
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
} 