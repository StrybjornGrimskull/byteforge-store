package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessMouseSpecDTO;
import com.byteforge.byteforge.repositories.WirelessMouseSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WirelessMouseSpecService {

    private final WirelessMouseSpecRepository wirelessMouseSpecRepository;

    public WirelessMouseSpecDTO getWirelessMouseSpecByProductId(Integer productId) {
        return wirelessMouseSpecRepository.findByProductId(productId)
                .map(WirelessMouseSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 