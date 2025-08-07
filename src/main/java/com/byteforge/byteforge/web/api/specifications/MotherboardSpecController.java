package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.MotherboardSpecDTO;
import com.byteforge.byteforge.services.specifications.MotherboardSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
} 