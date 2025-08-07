package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.SsdSpecDTO;
import com.byteforge.byteforge.services.specifications.SsdSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
} 