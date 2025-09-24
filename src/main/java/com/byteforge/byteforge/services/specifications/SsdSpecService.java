package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.SsdSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.SsdSpec;
import com.byteforge.byteforge.repositories.SsdSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class SsdSpecService {

    private final SsdSpecRepository ssdSpecRepository;

    public SsdSpecDTO getSsdSpecByProductId(Integer productId) {
        return ssdSpecRepository.findByProductId(productId)
                .map(spec -> new SsdSpecDTO(
                        spec.getCapacity(),
                        spec.getFormFactor(),
                        spec.getInterfaceType(),
                        spec.getReadSpeed(),
                        spec.getWriteSpeed(),
                        spec.getMemoryType(),
                        spec.getEnduranceTbw(),
                        spec.getDramCache(),
                        spec.getEncryption(),
                        spec.getThickness()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createSsdSpec(Product product, ProductCreateRequestDto request) {
        if (request.ssdSpec() == null) return;
        
        SsdSpec spec = new SsdSpec();
        spec.setProduct(product);
        spec.setCapacity(parseInt(request.ssdSpec().capacity()));
        spec.setFormFactor(request.ssdSpec().interfaceType());
        spec.setInterfaceType(request.ssdSpec().interfaceType());
        spec.setReadSpeed(parseInt(request.ssdSpec().readSpeed()));
        spec.setWriteSpeed(parseInt(request.ssdSpec().writeSpeed()));
        spec.setMemoryType(request.ssdSpec().interfaceType());
        spec.setEnduranceTbw(parseInt(request.ssdSpec().enduranceTbw()));
        spec.setDramCache(Boolean.parseBoolean(request.ssdSpec().dramCache()));
        spec.setEncryption(request.ssdSpec().encryption());
        spec.setThickness(parseBigDecimal(request.ssdSpec().thickness()));
        
        ssdSpecRepository.save(spec);
    }

    public void updateSsdSpec(Integer productId, SsdSpecDTO dto) {
        SsdSpec spec = ssdSpecRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
        if (dto.capacity() != null) spec.setCapacity(dto.capacity());
        if (dto.formFactor() != null) spec.setFormFactor(dto.formFactor());
        if (dto.interfaceType() != null) spec.setInterfaceType(dto.interfaceType());
        if (dto.readSpeed() != null) spec.setReadSpeed(dto.readSpeed());
        if (dto.writeSpeed() != null) spec.setWriteSpeed(dto.writeSpeed());
        if (dto.memoryType() != null) spec.setMemoryType(dto.memoryType());
        if (dto.enduranceTbw() != null) spec.setEnduranceTbw(dto.enduranceTbw());
        if (dto.dramCache() != null) spec.setDramCache(dto.dramCache());
        if (dto.encryption() != null) spec.setEncryption(dto.encryption());
        if (dto.thickness() != null) spec.setThickness(dto.thickness());
        ssdSpecRepository.save(spec);
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