package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.dto.request.BrandCreateRequestDto;
import com.byteforge.byteforge.services.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandApiController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        List<BrandDto> brands = brandService.getAllBrands();
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BrandDto>> getBrandsByCategory(@PathVariable Integer categoryId) {
        List<BrandDto> brands = brandService.getBrandsByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createBrand(@Valid BrandCreateRequestDto brandRequest) {
        brandService.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}