package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessKeyboardSpecDTO;
import com.byteforge.byteforge.services.specifications.WirelessKeyboardSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateWirelessKeyboardSpec(@PathVariable Integer productId,
                                                           @Valid @RequestBody WirelessKeyboardSpecDTO dto) {
        wirelessKeyboardSpecService.updateWirelessKeyboardSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 