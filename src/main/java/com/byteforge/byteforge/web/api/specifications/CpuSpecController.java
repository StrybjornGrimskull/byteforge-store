package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.CpuSpecDTO;
import com.byteforge.byteforge.services.specifications.CpuSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications/cpu")
@RequiredArgsConstructor
public class CpuSpecController {

    private final CpuSpecService cpuSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<CpuSpecDTO> getCpuSpecByProductId(@PathVariable Integer productId) {
        var cpuSpecDTO = cpuSpecService.getCpuSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(cpuSpecDTO);
    }
} 