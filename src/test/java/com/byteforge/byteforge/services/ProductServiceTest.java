package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.request.ProductUpdateRequestDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.entities.Category;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.StockQuantity;
import com.byteforge.byteforge.repositories.BrandRepository;
import com.byteforge.byteforge.repositories.CategoryRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.StockQuantityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private SpecificationFactory specificationFactory;

    @Mock
    private StockQuantityRepository stockQuantityRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Category testCategory;
    private Brand testBrand;
    private StockQuantity testStockQuantity;
    private ProductCreateRequestDto testCreateRequest;
    private ProductUpdateRequestDto testUpdateRequest;

    @BeforeEach
    void setUp() {
        // Создаем тестовую категорию
        testCategory = new Category();
        testCategory.setId(1);
        testCategory.setName("Test Category");
        testCategory.setSlug("test-category");

        // Создаем тестовый бренд
        testBrand = new Brand();
        testBrand.setId(1);
        testBrand.setName("Test Brand");
        testBrand.setLogoUrl("test-logo.webp");

        // Создаем тестовое количество на складе
        testStockQuantity = new StockQuantity();
        testStockQuantity.setQuantity(10);

        // Создаем тестовый продукт
        testProduct = new Product();
        testProduct.setId(1);
        testProduct.setName("Test Product");
        testProduct.setCategory(testCategory);
        testProduct.setBrand(testBrand);
        testProduct.setOriginalPrice(BigDecimal.valueOf(100.00));
        testProduct.setPrice(BigDecimal.valueOf(100.00));
        testProduct.setWarrantyMonths(24);
        testProduct.setReleaseYear(2023);
        testProduct.setShortDescription("Test Description");
        testProduct.setImageUrl("test-image.webp");
        testProduct.setDiscountPercentage(0);
        testProduct.setStockQuantity(testStockQuantity);

        // Создаем тестовый запрос на создание продукта
        testCreateRequest = new ProductCreateRequestDto(
                "Test Product", // productName
                1, // productCategory
                1, // productBrand
                BigDecimal.valueOf(100.00), // originalPrice
                24, // warrantyMonths
                2023, // releaseYear
                "Test Description", // shortDescription
                null, // productImage
                10, // stockQuantity
                null, null, null, null, null, null, null, null, null, null, null, null // все спецификации
        );

        // Создаем тестовый запрос на обновление продукта
        testUpdateRequest = new ProductUpdateRequestDto(
                "Updated Product", // name
                BigDecimal.valueOf(120.00), // originalPrice
                10, // discountPercentage
                "Updated Description", // shortDescription
                10, // stockQuantity
                1, // categoryId
                1, // brandId
                2024, // releaseYear
                36, // warrantyMonths
                null // productImage
        );
    }

    @Test
    void getProductById_ShouldReturnProductResponseDto() {
        // Arrange
        ProductResponseDto expectedDto = new ProductResponseDto(
                1, // id
                "Test Product", // name
                0, // discountPercentage
                BigDecimal.valueOf(100.00), // originalPrice
                BigDecimal.valueOf(100.00), // price
                1, // categoryId
                "Test Category", // categoryName
                "Test Brand", // brandName
                "test-logo.webp", // brandLogo
                24, // warrantyMonths
                2023, // releaseYear
                "Test Description", // shortDescription
                "test-image.webp", // imageUrl
                10 // stockQuantity
        );
        when(productRepository.findProductResponseDtoById(1)).thenReturn(expectedDto);

        // Act
        ProductResponseDto result = productService.getProductById(1);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDto.id(), result.id());
        assertEquals(expectedDto.name(), result.name());
        assertEquals(expectedDto.price(), result.price());
        assertEquals(expectedDto.imageUrl(), result.imageUrl());
        assertEquals(expectedDto.shortDescription(), result.shortDescription());
        assertEquals(expectedDto.warrantyMonths(), result.warrantyMonths());
        assertEquals(expectedDto.releaseYear(), result.releaseYear());
        assertEquals(expectedDto.discountPercentage(), result.discountPercentage());
        assertEquals(expectedDto.categoryName(), result.categoryName());
        assertEquals(expectedDto.brandName(), result.brandName());

        verify(productRepository).findProductResponseDtoById(1);
    }

    @Test
    void getProductsLazy_ShouldReturnProductListDtos() {
        // Arrange
        ProductListDto productListDto = new ProductListDto(
                1, // id
                "Test Product", // name
                "test-image.webp", // imageUrl
                0, // discountPercentage
                BigDecimal.valueOf(100.00), // originalPrice
                BigDecimal.valueOf(100.00), // price
                "Test Brand", // brandName
                1, // categoryId
                10 // stockQuantity
        );
        List<ProductListDto> expectedList = List.of(productListDto);
        when(productRepository.findProductListDtos(any(), any(), any(), any(), any(), any()))
                .thenReturn(expectedList);

        // Act
        List<ProductListDto> result = productService.getProductsLazy(
                null, null, null, null, null, null, 10
        );

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productListDto.id(), result.getFirst().id());
        assertEquals(productListDto.name(), result.getFirst().name());
        assertEquals(productListDto.price(), result.getFirst().price());
        assertEquals(productListDto.imageUrl(), result.getFirst().imageUrl());
        assertEquals(productListDto.brandName(), result.getFirst().brandName());

        verify(productRepository).findProductListDtos(null, null, null, null, null, null);
    }

    @Test
    void createProduct_ShouldCreateProductSuccessfully() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(brandRepository.findById(1)).thenReturn(Optional.of(testBrand));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(stockQuantityRepository.save(any(StockQuantity.class))).thenReturn(testStockQuantity);

        // Act
        Product result = productService.createProduct(testCreateRequest);

        // Assert
        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        assertEquals(testProduct.getCategory().getId(), result.getCategory().getId());
        assertEquals(testProduct.getBrand().getId(), result.getBrand().getId());
        assertEquals(testProduct.getOriginalPrice(), result.getOriginalPrice());
        assertEquals(testProduct.getPrice(), result.getPrice());
        assertEquals(testProduct.getWarrantyMonths(), result.getWarrantyMonths());
        assertEquals(testProduct.getReleaseYear(), result.getReleaseYear());
        assertEquals(testProduct.getShortDescription(), result.getShortDescription());
        assertEquals(testProduct.getDiscountPercentage(), result.getDiscountPercentage());

        verify(categoryRepository).findById(1);
        verify(brandRepository).findById(1);
        verify(productRepository).save(any(Product.class));
        verify(stockQuantityRepository).save(any(StockQuantity.class));
        verify(specificationFactory).createSpecificationForProduct(any(Product.class), eq(1), eq(testCreateRequest));
    }

    @Test
    void createProduct_ShouldThrowExceptionWhenCategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                productService.createProduct(testCreateRequest));
        
        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository).findById(1);
        verify(brandRepository, never()).findById(anyInt());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowExceptionWhenBrandNotFound() {
        // Arrange
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                productService.createProduct(testCreateRequest));
        
        assertEquals("Brand not found", exception.getMessage());
        verify(categoryRepository).findById(1);
        verify(brandRepository).findById(1);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldUpdateProductSuccessfully() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(brandRepository.findById(1)).thenReturn(Optional.of(testBrand));
        when(stockQuantityRepository.findByProductId(1)).thenReturn(Optional.of(testStockQuantity));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);
        when(stockQuantityRepository.save(any(StockQuantity.class))).thenReturn(testStockQuantity);

        // Act
        productService.updateProduct(1, testUpdateRequest);

        // Assert
        verify(productRepository).findById(1);
        verify(categoryRepository).findById(1);
        verify(brandRepository).findById(1);
        verify(stockQuantityRepository).findByProductId(1);
        verify(stockQuantityRepository).save(any(StockQuantity.class));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowExceptionWhenProductNotFound() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                productService.updateProduct(1, testUpdateRequest));
        
        assertEquals("Product not found", exception.getMessage());
        verify(productRepository).findById(1);
        verify(categoryRepository, never()).findById(anyInt());
        verify(brandRepository, never()).findById(anyInt());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowExceptionWhenCategoryNotFound() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                productService.updateProduct(1, testUpdateRequest));
        
        assertEquals("Category not found", exception.getMessage());
        verify(productRepository).findById(1);
        verify(categoryRepository).findById(1);
        verify(brandRepository, never()).findById(anyInt());
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowExceptionWhenBrandNotFound() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                productService.updateProduct(1, testUpdateRequest));
        
        assertEquals("Brand not found", exception.getMessage());
        verify(productRepository).findById(1);
        verify(categoryRepository).findById(1);
        verify(brandRepository).findById(1);
        verify(productRepository, never()).save(any(Product.class));
    }
}
