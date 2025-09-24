package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.request.ProductCreateRequestDto;
import com.byteforge.byteforge.dto.request.ProductUpdateRequestDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.entities.Category;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.StockQuantity;
import com.byteforge.byteforge.exceptions.ImageSaveException;
import com.byteforge.byteforge.repositories.BrandRepository;
import com.byteforge.byteforge.repositories.CategoryRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.StockQuantityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    
    public Product createProduct(ProductCreateRequestDto request) {
        // Получаем категорию и бренд
        Category category = categoryRepository.findById(request.productCategory())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        Brand brand = brandRepository.findById(request.productBrand())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        
        // Сохраняем изображение (если предоставлено)
        String imageUrl = null;
        if (request.productImage() != null && !request.productImage().isEmpty()) {
            try {
                imageUrl = saveImage(request.productImage());
            } catch (IOException e) {
                throw new ImageSaveException("Failed to save image", e);
            }
        }
        
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
        
        // Создаем запись о количестве на складе (если указано)
        if (request.stockQuantity() != null) {
            StockQuantity stockQuantity = new StockQuantity();
            stockQuantity.setProduct(savedProduct);
            stockQuantity.setQuantity(request.stockQuantity());
            stockQuantityRepository.save(stockQuantity);
        }
        
        // Создаем спецификации через фабрику
        specificationFactory.createSpecificationForProduct(savedProduct, category.getId(), request);
        
        return savedProduct;
    }

    public void updateProduct(Integer id, ProductUpdateRequestDto request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        product.setName(request.name());
        product.setShortDescription(request.shortDescription());
        product.setReleaseYear(request.releaseYear());
        product.setWarrantyMonths(request.warrantyMonths());
        product.setOriginalPrice(request.originalPrice());
        product.setDiscountPercentage(request.discountPercentage());
        
        // Recalculate price
        product.setPrice(calculateDiscountedPrice(product.getOriginalPrice(), product.getDiscountPercentage()));
        
        // Update image only if new image is provided
        if (request.productImage() != null && !request.productImage().isEmpty()) {
            try {
                product.setImageUrl(saveImage(request.productImage()));
            } catch (IOException e) {
                throw new ImageSaveException("Failed to save image", e);
            }
        }
        
        // Update category
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(category);
        
        // Update brand
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        product.setBrand(brand);
        
        // Update stock
        StockQuantity sq = stockQuantityRepository.findByProductId(product.getId())
                .orElseGet(() -> createNewStockQuantity(product));
        sq.setQuantity(request.stockQuantity());
        stockQuantityRepository.save(sq);
        
        productRepository.save(product);
    }

    private java.math.BigDecimal calculateDiscountedPrice(java.math.BigDecimal originalPrice, int discountPercentage) {
        return originalPrice.multiply(java.math.BigDecimal.valueOf(1 - (discountPercentage / 100.0)));
    }


    private StockQuantity createNewStockQuantity(Product product) {
        StockQuantity stockQuantity = new StockQuantity();
        stockQuantity.setProduct(product);
        return stockQuantity;
    }

    // kept saveImage for create use; updates go through DTO imageUrl
    
    private String saveImage(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + ".webp";
        Path uploadDir = Paths.get("src/main/resources/static/uploads/").toAbsolutePath().normalize();
        Path targetFile = uploadDir.resolve(uniqueFileName).normalize();
        
        Files.createDirectories(uploadDir);
        Files.write(targetFile, file.getBytes());
        
        return uniqueFileName;
    }
}