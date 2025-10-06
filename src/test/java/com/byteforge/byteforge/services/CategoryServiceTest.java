package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.CategoryDto;
import com.byteforge.byteforge.entities.Category;
import com.byteforge.byteforge.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category testCategory1;
    private Category testCategory2;
    private List<Category> testCategories;

    @BeforeEach
    void setUp() {
        // Создаем тестовые категории
        testCategory1 = new Category();
        testCategory1.setId(1);
        testCategory1.setName("Electronics");
        testCategory1.setSlug("electronics");

        testCategory2 = new Category();
        testCategory2.setId(2);
        testCategory2.setName("Clothing");
        testCategory2.setSlug("clothing");

        testCategories = List.of(testCategory1, testCategory2);
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategoryDtos() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(testCategories);

        // Act
        List<CategoryDto> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверяем первую категорию
        CategoryDto firstCategory = result.getFirst();
        assertEquals(testCategory1.getId(), firstCategory.id());
        assertEquals(testCategory1.getName(), firstCategory.name());
        assertEquals(testCategory1.getSlug(), firstCategory.slug());

        // Проверяем вторую категорию
        CategoryDto secondCategory = result.get(1);
        assertEquals(testCategory2.getId(), secondCategory.id());
        assertEquals(testCategory2.getName(), secondCategory.name());
        assertEquals(testCategory2.getSlug(), secondCategory.slug());

        verify(categoryRepository).findAll();
    }

    @Test
    void getAllCategories_ShouldReturnEmptyListWhenNoCategoriesExist() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(List.of());

        // Act
        List<CategoryDto> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(categoryRepository).findAll();
    }

    @Test
    void getAllCategories_ShouldReturnSingleCategoryWhenOnlyOneExists() {
        // Arrange
        List<Category> singleCategory = List.of(testCategory1);
        when(categoryRepository.findAll()).thenReturn(singleCategory);

        // Act
        List<CategoryDto> result = categoryService.getAllCategories();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        CategoryDto categoryDto = result.getFirst();
        assertEquals(testCategory1.getId(), categoryDto.id());
        assertEquals(testCategory1.getName(), categoryDto.name());
        assertEquals(testCategory1.getSlug(), categoryDto.slug());

        verify(categoryRepository).findAll();
    }
}
