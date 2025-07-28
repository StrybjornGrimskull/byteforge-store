package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @GetMapping("/lazy")
    public ResponseEntity<List<ProductResponseDto>> getProductsLazy(
            @RequestParam(required = false) Integer lastId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "12") int limit) {
        List<ProductResponseDto> products = productService.getProductsLazy(
                lastId, categoryId, brandId, minPrice, maxPrice, name, limit);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetailsJson(@PathVariable Integer id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
