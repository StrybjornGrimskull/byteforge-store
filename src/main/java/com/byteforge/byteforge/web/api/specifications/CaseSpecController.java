package com.byteforge.byteforge.web.api.specifications;

import com.byteforge.byteforge.dto.specifications.CaseSpecDTO;
import com.byteforge.byteforge.services.specifications.CaseSpecService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateCaseSpec(@PathVariable Integer productId,
                                               @Valid @RequestBody CaseSpecDTO dto) {
        caseSpecService.updateCaseSpec(productId, dto);
        return ResponseEntity.ok().build();
    }
} 