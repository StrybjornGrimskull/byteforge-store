package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.BrandDto;
import com.byteforge.byteforge.dto.request.BrandCreateRequestDto;
import com.byteforge.byteforge.entities.Brand;
import com.byteforge.byteforge.exceptions.LogoUploadException;
import com.byteforge.byteforge.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class BrandService {
    private final BrandRepository brandRepository;
    private final ResourceLoader resourceLoader;

    // Получить все бренды в алфавитном порядке
    @Transactional(readOnly = true)
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAllBrandDtosOrderedByName();
    }

    @Transactional
    public void createBrand(BrandCreateRequestDto request) {
        try {
            // Проверяем, существует ли бренд с таким именем
            if (brandRepository.existsByNameIgnoreCase(request.name())) {
                throw new IllegalArgumentException("Brand with name '" + request.name() + "' already exists");
            }

            // Сохраняем логотип и получаем путь
            String logoUrl = saveLogoFile(request.logo());

            // Создаем новый бренд
            Brand brand = new Brand();
            brand.setName(request.name());
            brand.setLogoUrl(logoUrl);

            // Сохраняем в базу данных
            brandRepository.save(brand);

        } catch (IOException e) {
            throw new LogoUploadException("Failed to save logo file for brand: " + request.name(), e);
        }
    }

    private String saveLogoFile(MultipartFile logoFile) throws IOException {
        if (logoFile == null || logoFile.isEmpty()) {
            throw new IllegalArgumentException("Logo file is required");
        }

        // Проверка MIME типа (безопаснее чем проверка имени файла)
        String contentType = logoFile.getContentType();
        if (contentType == null || !contentType.equals("image/webp")) {
            throw new IllegalArgumentException("Only WebP format is allowed");
        }

        // Современный подход с ResourceLoader
        String uniqueFileName = UUID.randomUUID() + ".webp";

        // Используем ResourceLoader для получения ресурса директории
        Resource uploadDirResource = resourceLoader.getResource("classpath:static/uploads/logo/");
        Path uploadDir = Paths.get(uploadDirResource.getURI()).toAbsolutePath().normalize();

        // Безопасное построение пути
        Path targetFile = uploadDir.resolve(uniqueFileName).normalize();

        // Проверка безопасности пути
        if (!targetFile.startsWith(uploadDir)) {
            throw new SecurityException("Path traversal attack detected");
        }

        // Читаем содержимое файла и сохраняем
        try (InputStream inputStream = logoFile.getInputStream()) {
            Files.write(targetFile, inputStream.readAllBytes());
        }

        return "logo/" + uniqueFileName;
    }
}