package com.byteforge.byteforge.specifications;

import com.byteforge.byteforge.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> hasCategoryId(Integer categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasBrandId(Integer brandId) {
        return (root, query, cb) -> brandId == null ? null : cb.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> hasMinPrice(Double minPrice) {
        return (root, query, cb) -> minPrice == null ? null : cb.ge(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(Double maxPrice) {
        return (root, query, cb) -> maxPrice == null ? null : cb.le(root.get("price"), maxPrice);
    }
    public static Specification<Product> hasNameLike(String name) {
        return (root, query, cb) ->
                name == null || name.isEmpty() ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasIdGreaterThan(Integer id) {
        return (root, query, cb) -> id == null ? null : cb.greaterThan(root.get("id"), id);
    }
}
