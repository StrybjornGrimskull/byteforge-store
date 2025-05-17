package com.byteforge.byteforge.utils;

import com.byteforge.byteforge.dto.ProductResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final ObjectMapper objectMapper;

    public ProductMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String convertSpecToJson(ProductResponseDto product) {
        if (product.spec() == null) {
            return "{}";
        }
        try {
            String json = objectMapper.writeValueAsString(product.spec());
            return json;
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}