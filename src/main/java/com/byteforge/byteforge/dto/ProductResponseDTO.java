package com.byteforge.byteforge.dto;

import com.byteforge.byteforge.dto.specifications.*;
import com.byteforge.byteforge.entities.Product;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Integer id,
        String name,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String brandName,
        String brandLogo,
        Integer warrantyMonths,
        Integer releaseYear,
        String shortDescription,
        String imageUrl,
        Integer stockQuantity,
        ProductSpecDTO spec
) {
    public static ProductResponseDTO fromEntity(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.getBrand().getLogoUrl(),
                product.getWarrantyMonths(),
                product.getReleaseYear(),
                product.getShortDescription(),
                product.getImageUrl(),
                product.getStockQuantity().getQuantity(),
                resolveSpec(product)
        );
    }

    private static ProductSpecDTO resolveSpec(Product product) {
        if (product.getCaseSpec() != null) {
            return CaseSpecDTO.fromEntity(product.getCaseSpec());
        } else if (product.getCpuSpec() != null) {
            return CpuSpecDTO.fromEntity(product.getCpuSpec());
        } else if (product.getGpuSpec() != null) {
            return GpuSpecDTO.fromEntity(product.getGpuSpec());
        } else if (product.getMonitorSpec() != null) {
            return MonitorSpecDTO.fromEntity(product.getMonitorSpec());
        } else if (product.getMotherboardSpec() != null) {
            return MotherboardSpecDTO.fromEntity(product.getMotherboardSpec());
        } else if (product.getPsuSpec() != null) {
            return PsuSpecDTO.fromEntity(product.getPsuSpec());
        } else if (product.getRamSpec() != null) {
            return RamSpecDTO.fromEntity(product.getRamSpec());
        } else if (product.getSsdSpec() != null) {
            return SsdSpecDTO.fromEntity(product.getSsdSpec());
        } else if (product.getWiredKeyboardSpec() != null) {
            return WiredKeyboardSpecDTO.fromEntity(product.getWiredKeyboardSpec());
        } else if (product.getWiredMouseSpec() != null) {
            return WiredMouseSpecDTO.fromEntity(product.getWiredMouseSpec());
        } else if (product.getWirelessKeyboardSpec() != null) {
            return WirelessKeyboardSpecDTO.fromEntity(product.getWirelessKeyboardSpec());
        } else if (product.getWirelessMouseSpec() != null) {
            return WirelessMouseSpecDTO.fromEntity(product.getWirelessMouseSpec());
        }
        return null;
    }
}
