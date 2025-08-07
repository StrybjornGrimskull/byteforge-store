package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.PsuSpecDTO;
import com.byteforge.byteforge.services.specifications.PsuSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications/psu")
@RequiredArgsConstructor
public class PsuSpecController {

    private final PsuSpecService psuSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<PsuSpecDTO> getPsuSpecByProductId(@PathVariable Integer productId) {
        var psuSpecDTO = psuSpecService.getPsuSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(psuSpecDTO);
    }
} 