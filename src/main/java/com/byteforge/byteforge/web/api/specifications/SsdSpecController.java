package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.SsdSpecDTO;
import com.byteforge.byteforge.services.specifications.SsdSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/specifications/ssd")
@RequiredArgsConstructor
public class SsdSpecController {

    private final SsdSpecService ssdSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<SsdSpecDTO> getSsdSpecByProductId(@PathVariable Integer productId) {
        var ssdSpecDTO = ssdSpecService.getSsdSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ssdSpecDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateSsdSpec(@PathVariable Integer productId,
                                           @Valid @RequestBody SsdSpecDTO dto) {
        ssdSpecService.updateSsdSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 