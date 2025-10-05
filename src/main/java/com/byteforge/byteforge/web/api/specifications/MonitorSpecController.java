package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.MonitorSpecDTO;
import com.byteforge.byteforge.services.specifications.MonitorSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/specifications/monitor")
@RequiredArgsConstructor
public class MonitorSpecController {

    private final MonitorSpecService monitorSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<MonitorSpecDTO> getMonitorSpecByProductId(@PathVariable Integer productId) {
        var monitorSpecDTO = monitorSpecService.getMonitorSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(monitorSpecDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateMonitorSpec(@PathVariable Integer productId,
                                               @Valid @RequestBody MonitorSpecDTO dto) {
        monitorSpecService.updateMonitorSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 