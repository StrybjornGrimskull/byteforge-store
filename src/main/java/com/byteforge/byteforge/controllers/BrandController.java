package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mvc/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public List<BrandDto> getBrandsByCategory(@RequestParam(required = false) Integer categoryId) {
        return brandService.getBrandsByCategory(categoryId);
    }
}