package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.ProductResponseDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductResponseDto> getProductsWithFilters(
            Integer categoryId,
            Integer brandId,
            Double minPrice,
            Double maxPrice,
            String name, // новый параметр
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Specification<Product> spec = Specification.where(null);

        if (categoryId != null) {
            spec = spec.and(ProductSpecifications.hasCategoryId(categoryId));
        }
        if (brandId != null) {
            spec = spec.and(ProductSpecifications.hasBrandId(brandId));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.hasMinPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.hasMaxPrice(maxPrice));
        }
        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecifications.hasNameLike(name));
        }

        return productRepository.findAll(spec, pageable)
                .map(ProductResponseDto::fromEntity);
    }

    public ProductResponseDto getProductById(Integer id) {
        return productRepository.findById(id)
                .map(ProductResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }


}