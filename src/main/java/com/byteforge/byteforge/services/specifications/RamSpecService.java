package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.RamSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.RamSpec;
import com.byteforge.byteforge.repositories.RamSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RamSpecService {

    private final RamSpecRepository ramSpecRepository;

    public RamSpecDTO getRamSpecByProductId(Integer productId) {
        return ramSpecRepository.findByProductId(productId)
                .map(spec -> new RamSpecDTO(
                        spec.getMemorySize(),
                        spec.getModulesCount(),
                        spec.getSpeed(),
                        spec.getType(),
                        spec.getTimings(),
                        spec.getVoltage()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createRamSpec(Product product, ProductCreateRequestDto request) {
        if (request.ramSpec() == null) return;
        
        RamSpec spec = new RamSpec();
        spec.setProduct(product);
        spec.setMemorySize(parseInt(request.ramSpec().modulesCount()));
        spec.setModulesCount(parseInt(request.ramSpec().modulesCount()));
        spec.setSpeed(parseInt(request.ramSpec().speed()));
        spec.setType(request.ramSpec().type());
        spec.setTimings(request.ramSpec().timings());
        spec.setVoltage(parseBigDecimal(request.ramSpec().voltage()));
        
        ramSpecRepository.save(spec);
    }

    public void updateRamSpec(Integer productId, RamSpecDTO dto) {
        RamSpec spec = ramSpecRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
        if (dto.memorySize() != null) spec.setMemorySize(dto.memorySize());
        if (dto.modulesCount() != null) spec.setModulesCount(dto.modulesCount());
        if (dto.speed() != null) spec.setSpeed(dto.speed());
        if (dto.type() != null) spec.setType(dto.type());
        if (dto.timings() != null) spec.setTimings(dto.timings());
        if (dto.voltage() != null) spec.setVoltage(dto.voltage());
        ramSpecRepository.save(spec);
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