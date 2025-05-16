package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.specifications.ProductSpecDTO;
import com.byteforge.byteforge.entities.Product;

public interface ProductSpecFactory {
    void createAndAttachSpec(ProductSpecDTO specDTO, Product product, String type);
}
