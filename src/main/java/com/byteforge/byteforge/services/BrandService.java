package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.repositories.BrandRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandDto::from)
                .collect(Collectors.toList());
    }
    // Получить бренды по категории (с преобразованием в DTO)
    @Transactional(readOnly = true)
    public List<BrandDto> getBrandsByCategory(Integer categoryId) {
        if (categoryId == null) {
            return getAllBrands();
        }

        return brandRepository.findByProductsCategoryId(categoryId).stream()
                .map(BrandDto::from)
                .collect(Collectors.toList());
    }
}

