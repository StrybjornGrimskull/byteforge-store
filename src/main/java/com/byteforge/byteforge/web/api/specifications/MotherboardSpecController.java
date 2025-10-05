package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.MotherboardSpecDTO;
import com.byteforge.byteforge.services.specifications.MotherboardSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/motherboard")
@RequiredArgsConstructor
public class MotherboardSpecController {

    private final MotherboardSpecService motherboardSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<MotherboardSpecDTO> getMotherboardSpecByProductId(@PathVariable Integer productId) {
        var motherboardSpecDTO = motherboardSpecService.getMotherboardSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(motherboardSpecDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateMotherboardSpec(@PathVariable Integer productId,
                                                   @Valid @RequestBody MotherboardSpecDTO dto) {
        motherboardSpecService.updateMotherboardSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 