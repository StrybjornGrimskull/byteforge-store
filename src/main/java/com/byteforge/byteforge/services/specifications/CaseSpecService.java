package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.CaseSpecDTO;
import com.byteforge.byteforge.repositories.CaseSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseSpecService {

    private final CaseSpecRepository caseSpecRepository;

    public CaseSpecDTO getCaseSpecByProductId(Integer productId) {
        return caseSpecRepository.findByProductId(productId)
                .map(CaseSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
}