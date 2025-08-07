package com.byteforge.byteforge.services.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessKeyboardSpecDTO;
import com.byteforge.byteforge.repositories.WirelessKeyboardSpecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WirelessKeyboardSpecService {

    private final WirelessKeyboardSpecRepository wirelessKeyboardSpecRepository;

    public WirelessKeyboardSpecDTO getWirelessKeyboardSpecByProductId(Integer productId) {
        return wirelessKeyboardSpecRepository.findByProductId(productId)
                .map(WirelessKeyboardSpecDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Specification not found with id: " + productId));
    }
} 