package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.entities.Product;
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
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductCreateRequestDto request) {
        try {
            Product product = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Product created successfully with ID: " + product.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error: " + e.getMessage());
        }
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
}