package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.GpuSpecDTO;
import com.byteforge.byteforge.services.specifications.GpuSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/gpu")
@RequiredArgsConstructor
public class GpuSpecController {

    private final GpuSpecService gpuSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<GpuSpecDTO> getGpuSpecByProductId(@PathVariable Integer productId) {
        var gpuSpecDTO = gpuSpecService.getGpuSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(gpuSpecDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateGpuSpec(@PathVariable Integer productId,
                                              @Valid @RequestBody GpuSpecDTO dto) {
        gpuSpecService.updateGpuSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 