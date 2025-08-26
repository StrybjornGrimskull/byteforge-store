package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandDto(
                        brand.getId(),
                        brand.getName(),
                        brand.getLogoUrl()
                ))
                .toList();
    }
    
    // Получить бренды по категории (с преобразованием в DTO)
    @Transactional(readOnly = true)
    public List<BrandDto> getBrandsByCategory(Integer categoryId) {
        if (categoryId == null) {
            return getAllBrands();
        }

        return brandRepository.findByProductsCategoryId(categoryId).stream()
                .map(brand -> new BrandDto(
                        brand.getId(),
                        brand.getName(),
                        brand.getLogoUrl()
                ))
                .toList();
    }
}

