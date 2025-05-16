package com.byteforge.byteforge.services;

import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.repositories.BrandRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    // Получить все бренды
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    // Получить бренды по категории (новый метод)
    public List<Brand> getBrandsByCategory(Integer categoryId) {
        if (categoryId == null) {
            return getAllBrands();
        }
        return brandRepository.findByProductsCategoryId(categoryId);
    }
}
