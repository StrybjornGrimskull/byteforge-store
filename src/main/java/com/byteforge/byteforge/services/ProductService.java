package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public List<ProductResponseDto> getProductsLazy(
            Integer lastId,
            Integer categoryId,
            Integer brandId,
            Double minPrice,
            Double maxPrice,
            String name,
            int limit) {

        Specification<Product> spec = Specification.where(null);

        // Добавляем условие для cursor-based пагинации
        if (lastId != null) {
            spec = spec.and(ProductSpecifications.hasIdGreaterThan(lastId));
        }

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

        // Создаем Pageable с лимитом и сортировкой по ID
        Pageable pageable = PageRequest.of(0, limit, Sort.by("id").ascending());

        return productRepository.findAll(spec, pageable)
                .getContent()
                .stream()
                .map(ProductResponseDto::fromEntity)
                .toList();
    }
}