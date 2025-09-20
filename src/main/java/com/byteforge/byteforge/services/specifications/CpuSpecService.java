package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.CpuSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.CpuSpec;
import com.byteforge.byteforge.repositories.CpuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public void createCpuSpec(Product product, ProductCreateRequestDto request) {
        if (request.cpuSpec() == null) return;
        
        CpuSpec spec = new CpuSpec();
        spec.setProduct(product);
        spec.setSocket(request.cpuSpec().socket());
        spec.setCores(parseInt(request.cpuSpec().cores()));
        spec.setThreads(parseInt(request.cpuSpec().threads()));
        spec.setBaseClock(parseBigDecimal(request.cpuSpec().baseClock()));
        spec.setBoostClock(parseBigDecimal(request.cpuSpec().boostClock()));
        spec.setCacheSize(parseInt(request.cpuSpec().cache()));
        spec.setTdp(parseInt(request.cpuSpec().tdp()));
        spec.setIntegratedGpu(parseBoolean(request.cpuSpec().integratedGraphics()));
        
        cpuSpecRepository.save(spec);
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

    private Boolean parseBoolean(String value) {
        if (value == null || value.trim().isEmpty()) return false;
        return Boolean.parseBoolean(value.trim());
    }
}