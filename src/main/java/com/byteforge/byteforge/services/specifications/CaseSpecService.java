package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.specifications.CaseSpecDTO;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.specifications.CaseSpec;
import com.byteforge.byteforge.repositories.CaseSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseSpecService {

    private final CaseSpecRepository caseSpecRepository;

    public CaseSpecDTO getCaseSpecByProductId(Integer productId) {
        return caseSpecRepository.findByProductId(productId)
                .map(spec -> new CaseSpecDTO(
                        spec.getFormFactor(),
                        spec.getMotherboardSupport(),
                        spec.getMaxGpuLength(),
                        spec.getMaxCpuCoolerHeight(),
                        spec.getFansIncluded(),
                        spec.getRadiatorSupport()
                ))
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }

    public void createCaseSpec(Product product, ProductCreateRequestDto request) {
        if (request.caseSpec() == null) return;
        
        CaseSpec spec = new CaseSpec();
        spec.setProduct(product);
        spec.setFormFactor(request.caseSpec().formFactor());
        spec.setMotherboardSupport(request.caseSpec().motherboardSupport());
        spec.setMaxGpuLength(parseInt(request.caseSpec().maxGpuLength()));
        spec.setMaxCpuCoolerHeight(parseInt(request.caseSpec().maxCpuCoolerHeight()));
        spec.setFansIncluded(parseInt(request.caseSpec().fansIncluded()));
        spec.setRadiatorSupport(request.caseSpec().radiatorSupport());
        
        caseSpecRepository.save(spec);
    }

    public void updateCaseSpec(Integer productId, CaseSpecDTO dto) {
        CaseSpec spec = caseSpecRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
        if (dto.formFactor() != null) spec.setFormFactor(dto.formFactor());
        if (dto.motherboardSupport() != null) spec.setMotherboardSupport(dto.motherboardSupport());
        if (dto.maxGpuLength() != null) spec.setMaxGpuLength(dto.maxGpuLength());
        if (dto.maxCpuCoolerHeight() != null) spec.setMaxCpuCoolerHeight(dto.maxCpuCoolerHeight());
        if (dto.fansIncluded() != null) spec.setFansIncluded(dto.fansIncluded());
        if (dto.radiatorSupport() != null) spec.setRadiatorSupport(dto.radiatorSupport());
        caseSpecRepository.save(spec);
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