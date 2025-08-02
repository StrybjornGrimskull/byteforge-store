package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(ProductResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<ProductListDto> getProductsLazy(
            Integer lastId,
            Integer categoryId,
            Integer brandId,
            Double minPrice,
            Double maxPrice,
            String name,
            int limit) {

        List<ProductListDto> allProducts = productRepository.findProductListDtos(
                lastId, categoryId, brandId, minPrice, maxPrice, name);

        // Применяем лимит вручную
        return allProducts.stream()
                .limit(limit)
                .toList();
    }
}