package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> listProductsJson(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size) {
        Page<ProductResponseDto> pageResult = productService.getProductsWithFilters(
                categoryId, brandId, minPrice, maxPrice, name, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetailsJson(@PathVariable Integer id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
