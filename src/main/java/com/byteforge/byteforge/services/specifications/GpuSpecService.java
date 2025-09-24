package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.GpuSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.GpuSpec;
import com.byteforge.byteforge.repositories.GpuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GpuSpecService {

    private final GpuSpecRepository gpuSpecRepository;

    public GpuSpecDTO getGpuSpecByProductId(Integer productId) {
        return gpuSpecRepository.findByProductId(productId)
                .map(spec -> new GpuSpecDTO(
                        spec.getMemorySize(),
                        spec.getMemoryType(),
                        spec.getBusWidth(),
                        spec.getBaseClock(),
                        spec.getBoostClock(),
                        spec.getTdp(),
                        spec.getLength(),
                        spec.getDisplayOutputs()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createGpuSpec(Product product, ProductCreateRequestDto request) {
        if (request.gpuSpec() == null) return;
        
        GpuSpec spec = new GpuSpec();
        spec.setProduct(product);
        spec.setMemorySize(parseInt(request.gpuSpec().memorySize()));
        spec.setMemoryType(request.gpuSpec().memoryType());
        spec.setBusWidth(parseInt(request.gpuSpec().memoryBus()));
        spec.setBaseClock(parseInt(request.gpuSpec().gpuBaseClock()));
        spec.setBoostClock(parseInt(request.gpuSpec().gpuBoostClock()));
        spec.setTdp(parseInt(request.gpuSpec().gpuTdp()));
        spec.setLength(parseInt(request.gpuSpec().length()));
        spec.setDisplayOutputs(request.gpuSpec().outputs());
        
        gpuSpecRepository.save(spec);
    }

    public void updateGpuSpec(Integer productId, GpuSpecDTO dto) {
        GpuSpec spec = gpuSpecRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
        if (dto.memorySize() != null) spec.setMemorySize(dto.memorySize());
        if (dto.memoryType() != null) spec.setMemoryType(dto.memoryType());
        if (dto.busWidth() != null) spec.setBusWidth(dto.busWidth());
        if (dto.baseClock() != null) spec.setBaseClock(dto.baseClock());
        if (dto.boostClock() != null) spec.setBoostClock(dto.boostClock());
        if (dto.tdp() != null) spec.setTdp(dto.tdp());
        if (dto.length() != null) spec.setLength(dto.length());
        if (dto.displayOutputs() != null) spec.setDisplayOutputs(dto.displayOutputs());
        gpuSpecRepository.save(spec);
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
} 