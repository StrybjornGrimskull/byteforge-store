package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.MotherboardSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.MotherboardSpec;
import com.byteforge.byteforge.repositories.MotherboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotherboardSpecService {

    private final MotherboardSpecRepository motherboardSpecRepository;

    public MotherboardSpecDTO getMotherboardSpecByProductId(Integer productId) {
        return motherboardSpecRepository.findByProductId(productId)
                .map(spec -> new MotherboardSpecDTO(
                        spec.getSocket(),
                        spec.getChipset(),
                        spec.getFormFactor(),
                        spec.getMemorySlots(),
                        spec.getMaxMemory(),
                        spec.getMemoryType(),
                        spec.getM2Slots(),
                        spec.getSataPorts()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createMotherboardSpec(Product product, ProductCreateRequestDto request) {
        if (request.motherboardSpec() == null) return;
        
        MotherboardSpec spec = new MotherboardSpec();
        spec.setProduct(product);
        spec.setSocket(request.motherboardSpec().chipset());
        spec.setChipset(request.motherboardSpec().chipset());
        spec.setFormFactor(request.motherboardSpec().chipset());
        spec.setMemorySlots(parseInt(request.motherboardSpec().memorySlots()));
        spec.setMaxMemory(parseInt(request.motherboardSpec().maxMemory()));
        spec.setMemoryType(request.motherboardSpec().chipset());
        spec.setM2Slots(parseInt(request.motherboardSpec().m2Slots()));
        spec.setSataPorts(parseInt(request.motherboardSpec().sataPorts()));
        
        motherboardSpecRepository.save(spec);
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