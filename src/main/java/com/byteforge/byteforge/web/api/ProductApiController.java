package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.request.ProductUpdateRequestDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {
    
    private final ProductService productService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createProduct(@Valid @ModelAttribute ProductCreateRequestDto request) {
        productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    @GetMapping("/lazy")
    public ResponseEntity<List<ProductListDto>> getProductsLazy(
            @RequestParam(required = false) Integer lastId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "12") int limit) {
        
        List<ProductListDto> products = productService.getProductsLazy(
                lastId, categoryId, brandId, minPrice, maxPrice, name, limit);
        
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Integer id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PRODUCT_MANAGER')")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer id,
                                           @Valid @ModelAttribute ProductUpdateRequestDto request) {
        productService.updateProduct(id, request);
        return ResponseEntity.ok().build();
    }
}