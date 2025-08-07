package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.CaseSpecDTO;
import com.byteforge.byteforge.services.specifications.CaseSpecService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/specifications/case")
@RequiredArgsConstructor
public class CaseSpecController {

    private final CaseSpecService caseSpecService;

    @GetMapping("/{productId}")
    public ResponseEntity<CaseSpecDTO> getCaseSpecByProductId(@PathVariable Integer productId) {
        var caseSpecDTO = caseSpecService.getCaseSpecByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(caseSpecDTO);
    }
} 