package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessKeyboardSpecDTO;
import com.byteforge.byteforge.services.specifications.WirelessKeyboardSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications/wireless-keyboard")
@RequiredArgsConstructor
public class WirelessKeyboardSpecController {

    private final WirelessKeyboardSpecService wirelessKeyboardSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<WirelessKeyboardSpecDTO> getWirelessKeyboardSpecByProductId(@PathVariable Integer productId) {
        var wirelessKeyboardSpec = wirelessKeyboardSpecService.getWirelessKeyboardSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(wirelessKeyboardSpec);
    }
} 