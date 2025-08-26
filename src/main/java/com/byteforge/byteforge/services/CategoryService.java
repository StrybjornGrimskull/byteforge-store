package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.CategoryDto;
import com.byteforge.byteforge.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(
                        category.getId(),
                        category.getName(),
                        category.getSlug()
                ))
                .toList();
    }
}

