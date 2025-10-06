package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.dto.request.BrandCreateRequestDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.exceptions.LogoUploadException;
import com.byteforge.byteforge.repositories.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private MultipartFile mockMultipartFile;

    @Mock
    private Resource mockResource;

    @InjectMocks
    private BrandService brandService;

    private List<BrandDto> testBrandDtos;
    private BrandCreateRequestDto testCreateRequest;

    @BeforeEach
    void setUp() {
        // Создаем тестовые бренды
        Brand testBrand1 = new Brand();
        testBrand1.setId(1);
        testBrand1.setName("Apple");
        testBrand1.setLogoUrl("logo/apple.webp");

        Brand testBrand2 = new Brand();
        testBrand2.setId(2);
        testBrand2.setName("Samsung");
        testBrand2.setLogoUrl("logo/samsung.webp");

        // Создаем тестовые DTO
        testBrandDtos = List.of(
                new BrandDto(1, "Apple", "logo/apple.webp"),
                new BrandDto(2, "Samsung", "logo/samsung.webp")
        );

        // Создаем тестовый запрос на создание бренда
        testCreateRequest = new BrandCreateRequestDto("Test Brand", mockMultipartFile);
    }

    @Test
    void getAllBrands_ShouldReturnListOfBrandDtos() {
        // Arrange
        when(brandRepository.findAllBrandDtosOrderedByName()).thenReturn(testBrandDtos);

        // Act
        List<BrandDto> result = brandService.getAllBrands();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        // Проверяем первый бренд
        BrandDto firstBrand = result.getFirst();
        assertEquals(testBrandDtos.getFirst().id(), firstBrand.id());
        assertEquals(testBrandDtos.getFirst().name(), firstBrand.name());
        assertEquals(testBrandDtos.getFirst().logoUrl(), firstBrand.logoUrl());

        // Проверяем второй бренд
        BrandDto secondBrand = result.get(1);
        assertEquals(testBrandDtos.get(1).id(), secondBrand.id());
        assertEquals(testBrandDtos.get(1).name(), secondBrand.name());
        assertEquals(testBrandDtos.get(1).logoUrl(), secondBrand.logoUrl());

        verify(brandRepository).findAllBrandDtosOrderedByName();
    }

    @Test
    void getAllBrands_ShouldReturnEmptyListWhenNoBrandsExist() {
        // Arrange
        when(brandRepository.findAllBrandDtosOrderedByName()).thenReturn(List.of());

        // Act
        List<BrandDto> result = brandService.getAllBrands();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(brandRepository).findAllBrandDtosOrderedByName();
    }

    @Test
    void getBrandsByCategory_ShouldReturnBrandDtosForCategory() {
        // Arrange
        List<BrandDto> categoryBrands = List.of(testBrandDtos.getFirst());
        when(brandRepository.findBrandDtosByProductsCategoryId(1)).thenReturn(categoryBrands);

        // Act
        List<BrandDto> result = brandService.getBrandsByCategory(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testBrandDtos.getFirst().id(), result.getFirst().id());
        assertEquals(testBrandDtos.getFirst().name(), result.getFirst().name());
        assertEquals(testBrandDtos.getFirst().logoUrl(), result.getFirst().logoUrl());

        verify(brandRepository).findBrandDtosByProductsCategoryId(1);
    }

    @Test
    void getBrandsByCategory_ShouldReturnEmptyListWhenNoBrandsInCategory() {
        // Arrange
        when(brandRepository.findBrandDtosByProductsCategoryId(999)).thenReturn(List.of());

        // Act
        List<BrandDto> result = brandService.getBrandsByCategory(999);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(brandRepository).findBrandDtosByProductsCategoryId(999);
    }

    @Test
    void createBrand_ShouldCreateBrandSuccessfully() throws IOException {
        // Arrange
        when(brandRepository.existsByNameIgnoreCase("Test Brand")).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("image/webp");
        when(mockMultipartFile.getInputStream()).thenReturn(new ByteArrayInputStream("test image data".getBytes()));
        when(resourceLoader.getResource("classpath:static/uploads/logo/")).thenReturn(mockResource);
        when(mockResource.getURI()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir")).toUri());
        Brand savedBrand = new Brand();
        savedBrand.setId(1);
        savedBrand.setName("Test Brand");
        savedBrand.setLogoUrl("logo/test-brand.webp");
        when(brandRepository.save(any(Brand.class))).thenReturn(savedBrand);

        // Act
        brandService.createBrand(testCreateRequest);

        // Assert
        verify(brandRepository).existsByNameIgnoreCase("Test Brand");
        verify(mockMultipartFile).getContentType();
        verify(mockMultipartFile).getInputStream();
        verify(resourceLoader).getResource("classpath:static/uploads/logo/");
        verify(brandRepository).save(any(Brand.class));
    }

    @Test
    void createBrand_ShouldThrowExceptionWhenBrandNameAlreadyExists() {
        // Arrange
        when(brandRepository.existsByNameIgnoreCase("Test Brand")).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandService.createBrand(testCreateRequest));

        assertEquals("Brand with name 'Test Brand' already exists", exception.getMessage());
        verify(brandRepository).existsByNameIgnoreCase("Test Brand");
        verify(mockMultipartFile, never()).getContentType();
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void createBrand_ShouldThrowExceptionWhenInvalidImageFormat() throws IOException {
        // Arrange
        when(brandRepository.existsByNameIgnoreCase("Test Brand")).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("image/jpeg");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandService.createBrand(testCreateRequest));

        assertEquals("Only WebP format is allowed", exception.getMessage());
        verify(brandRepository).existsByNameIgnoreCase("Test Brand");
        verify(mockMultipartFile).getContentType();
        verify(mockMultipartFile, never()).getInputStream();
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void createBrand_ShouldThrowExceptionWhenContentTypeIsNull() throws IOException {
        // Arrange
        when(brandRepository.existsByNameIgnoreCase("Test Brand")).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                brandService.createBrand(testCreateRequest));

        assertEquals("Only WebP format is allowed", exception.getMessage());
        verify(brandRepository).existsByNameIgnoreCase("Test Brand");
        verify(mockMultipartFile).getContentType();
        verify(mockMultipartFile, never()).getInputStream();
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void createBrand_ShouldThrowLogoUploadExceptionWhenIOExceptionOccurs() throws IOException {
        // Arrange
        when(brandRepository.existsByNameIgnoreCase("Test Brand")).thenReturn(false);
        when(mockMultipartFile.getContentType()).thenReturn("image/webp");
        when(mockMultipartFile.getInputStream()).thenThrow(new IOException("File read error"));
        when(resourceLoader.getResource("classpath:static/uploads/logo/")).thenReturn(mockResource);
        when(mockResource.getURI()).thenReturn(Paths.get(System.getProperty("java.io.tmpdir")).toUri());

        // Act & Assert
        LogoUploadException exception = assertThrows(LogoUploadException.class, () ->
                brandService.createBrand(testCreateRequest));

        assertEquals("Failed to save logo file for brand: Test Brand", exception.getMessage());
        assertInstanceOf(IOException.class, exception.getCause());
        verify(brandRepository).existsByNameIgnoreCase("Test Brand");
        verify(mockMultipartFile).getContentType();
        verify(mockMultipartFile).getInputStream();
        verify(brandRepository, never()).save(any(Brand.class));
    }
}
