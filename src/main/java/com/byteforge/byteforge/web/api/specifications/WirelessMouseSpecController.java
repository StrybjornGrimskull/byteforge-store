package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WirelessMouseSpecDTO;
import com.byteforge.byteforge.services.specifications.WirelessMouseSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
} 