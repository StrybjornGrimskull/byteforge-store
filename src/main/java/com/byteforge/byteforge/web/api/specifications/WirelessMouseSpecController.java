package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessMouseSpecDTO;
import com.byteforge.byteforge.services.specifications.WirelessMouseSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/wireless-mouse")
@RequiredArgsConstructor
public class WirelessMouseSpecController {

    private final WirelessMouseSpecService wirelessMouseSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<WirelessMouseSpecDTO> getWirelessMouseSpecByProductId(@PathVariable Integer productId) {
        var wirelessMouseSpec = wirelessMouseSpecService.getWirelessMouseSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(wirelessMouseSpec);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateWirelessMouseSpec(@PathVariable Integer productId,
                                                     @Valid @RequestBody WirelessMouseSpecDTO dto) {
        wirelessMouseSpecService.updateWirelessMouseSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 