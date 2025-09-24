package com.byteforge.byteforge.dto.request;

import com.byteforge.byteforge.dto.request.specifications.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record ProductCreateRequestDto(
    
    @NotBlank(message = "Product name is required")
    String productName,
    
    @NotNull(message = "Category is required")
    Integer productCategory,
    
    @NotNull(message = "Brand is required")
    Integer productBrand,
    
    @NotNull(message = "Original price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal originalPrice,
    
    @NotNull(message = "Warranty is required")
    @Min(value = 0)
    Integer warrantyMonths,
    
    @NotNull(message = "Release year is required")
    @Min(value = 1990)
    @Max(value = 2030)
    Integer releaseYear,
    
    @NotBlank(message = "Short description is required")
    String shortDescription,
    
    @NotNull(message = "Please select a product image file")
    MultipartFile productImage,
    
    Integer stockQuantity,
    
    // Спецификации продуктов - все Request DTO для каждого типа
    CaseSpecRequestDto caseSpec,
    CpuSpecRequestDto cpuSpec,
    GpuSpecRequestDto gpuSpec,
    MonitorSpecRequestDto monitorSpec,
    MotherboardSpecRequestDto motherboardSpec,
    PsuSpecRequestDto psuSpec,
    RamSpecRequestDto ramSpec,
    SsdSpecRequestDto ssdSpec,
    WiredKeyboardSpecRequestDto wiredKeyboardSpec,
    WirelessKeyboardSpecRequestDto wirelessKeyboardSpec,
    WiredMouseSpecRequestDto wiredMouseSpec,
    WirelessMouseSpecRequestDto wirelessMouseSpec
) {}