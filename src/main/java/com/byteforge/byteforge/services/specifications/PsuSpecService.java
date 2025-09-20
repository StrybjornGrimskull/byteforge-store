package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.PsuSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.PsuSpec;
import com.byteforge.byteforge.repositories.PsuSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PsuSpecService {

    private final PsuSpecRepository psuSpecRepository;

    public PsuSpecDTO getPsuSpecByProductId(Integer productId) {
        return psuSpecRepository.findByProductId(productId)
                .map(spec -> new PsuSpecDTO(
                        spec.getWattage(),
                        spec.getFormFactor(),
                        spec.getEfficiencyCert(),
                        spec.getModularity(),
                        spec.getPcie8pinConnectors(),
                        spec.getSataConnectors()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createPsuSpec(Product product, ProductCreateRequestDto request) {
        if (request.psuSpec() == null) return;
        
        PsuSpec spec = new PsuSpec();
        spec.setProduct(product);
        spec.setWattage(parseInt(request.psuSpec().wattage()));
        spec.setFormFactor(request.psuSpec().efficiencyCert());
        spec.setEfficiencyCert(request.psuSpec().efficiencyCert());
        spec.setModularity(request.psuSpec().modularity());
        spec.setPcie8pinConnectors(parseInt(request.psuSpec().pcie8pinConnectors()));
        spec.setSataConnectors(parseInt(request.psuSpec().sataConnectors()));
        
        psuSpecRepository.save(spec);
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