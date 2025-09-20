package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.entities.Category;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.StockQuantity;
import com.byteforge.byteforge.repositories.BrandRepository;
import com.byteforge.byteforge.repositories.CategoryRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.StockQuantityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final SpecificationFactory specificationFactory;
    private final ResourceLoader resourceLoader;
    private final StockQuantityRepository stockQuantityRepository;

    public ProductResponseDto getProductById(Integer id) {
        return productRepository.findProductResponseDtoById(id);
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
    
    public Product createProduct(ProductCreateRequestDto request) throws IOException {
        // Получаем категорию и бренд
        Category category = categoryRepository.findById(request.productCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Brand brand = brandRepository.findById(request.productBrand())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        
        // Сохраняем изображение
        String imageUrl = saveImage(request.productImage());
        
        // Создаем продукт
        Product product = new Product();
        product.setName(request.productName());
        product.setCategory(category);
        product.setBrand(brand);
        product.setOriginalPrice(request.originalPrice());
        product.setPrice(request.originalPrice());
        product.setWarrantyMonths(request.warrantyMonths());
        product.setReleaseYear(request.releaseYear());
        product.setShortDescription(request.shortDescription());
        product.setImageUrl(imageUrl);
        product.setDiscountPercentage(0);
        
        Product savedProduct = productRepository.save(product);
        
        // Создаем запись о количестве на складе
        StockQuantity stockQuantity = new StockQuantity();
        stockQuantity.setProduct(savedProduct);
        stockQuantity.setQuantity(request.stockQuantity());
        stockQuantityRepository.save(stockQuantity);
        
        // Создаем спецификации через фабрику
        specificationFactory.createSpecificationForProduct(savedProduct, category.getId(), request);
        
        return savedProduct;
    }
    
    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        // Проверка MIME типа - только WebP
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("image/webp")) {
            throw new IllegalArgumentException("Only WebP format is allowed");
        }

        // Генерируем уникальное имя файла с расширением .webp
        String uniqueFileName = UUID.randomUUID() + ".webp";

        // Используем ResourceLoader для получения ресурса директории uploads
        Resource uploadDirResource = resourceLoader.getResource("classpath:static/uploads/");
        Path uploadDir = Paths.get(uploadDirResource.getURI()).toAbsolutePath().normalize();

        // Безопасное построение пути
        Path targetFile = uploadDir.resolve(uniqueFileName).normalize();

        // Проверка безопасности пути
        if (!targetFile.startsWith(uploadDir)) {
            throw new SecurityException("Path traversal attack detected");
        }

        // Читаем содержимое файла и сохраняем
        try (InputStream inputStream = file.getInputStream()) {
            Files.write(targetFile, inputStream.readAllBytes());
        }

        return uniqueFileName;
    }
}