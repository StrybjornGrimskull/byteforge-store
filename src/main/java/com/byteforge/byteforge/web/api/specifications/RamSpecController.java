package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.RamSpecDTO;
import com.byteforge.byteforge.services.specifications.RamSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/ram")
@RequiredArgsConstructor
public class RamSpecController {

    private final RamSpecService ramSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<RamSpecDTO> getRamSpecByProductId(@PathVariable Integer productId) {
        var ramSpecDTO = ramSpecService.getRamSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ramSpecDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateRamSpec(@PathVariable Integer productId,
                                           @Valid @RequestBody RamSpecDTO dto) {
        ramSpecService.updateRamSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 