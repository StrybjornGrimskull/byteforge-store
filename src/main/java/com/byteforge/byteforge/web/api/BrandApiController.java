package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandApiController {

    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<BrandDto>> getBrandsByCategory(@RequestParam(required = false) Integer categoryId) {
        List<BrandDto> brands = brandService.getBrandsByCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(brands);
    }
}