package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.WiredKeyboardSpecDTO;
import com.byteforge.byteforge.services.specifications.WiredKeyboardSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications/wired-keyboard")
@RequiredArgsConstructor
public class WiredKeyboardSpecController {

    private final WiredKeyboardSpecService wiredKeyboardSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<WiredKeyboardSpecDTO> getWiredKeyboardSpecByProductId(@PathVariable Integer productId) {
        var wiredKeyboardSpec = wiredKeyboardSpecService.getWiredKeyboardSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(wiredKeyboardSpec);
    }
} 