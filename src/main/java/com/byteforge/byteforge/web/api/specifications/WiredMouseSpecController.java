package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WiredMouseSpecDTO;
import com.byteforge.byteforge.services.specifications.WiredMouseSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/wired-mouse")
@RequiredArgsConstructor
public class WiredMouseSpecController {

    private final WiredMouseSpecService wiredMouseSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<WiredMouseSpecDTO> getWiredMouseSpecByProductId(@PathVariable Integer productId) {
        var wiredMouseSpec = wiredMouseSpecService.getWiredMouseSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(wiredMouseSpec);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateWiredMouseSpec(@PathVariable Integer productId,
                                                     @Valid @RequestBody WiredMouseSpecDTO dto) {
        wiredMouseSpecService.updateWiredMouseSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 