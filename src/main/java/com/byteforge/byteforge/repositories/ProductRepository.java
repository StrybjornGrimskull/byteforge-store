package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.dto.response.ProductResponseDto;
import com.byteforge.byteforge.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query("SELECT new com.byteforge.byteforge.dto.ProductListDto(" +
           "p.id, p.name, p.imageUrl, p.price, b.name, p.category.id, " +
           "sq.quantity) " +
           "FROM Product p " +
           "JOIN p.brand b " +
           "LEFT JOIN p.stockQuantity sq " +
           "WHERE (:lastId IS NULL OR p.id > :lastId) " +
           "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
           "AND (:brandId IS NULL OR p.brand.id = :brandId) " +
           "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
           "AND (:name IS NULL OR :name = '' OR p.name LIKE '%' || :name || '%') " +
           "ORDER BY p.id ASC")
    List<ProductListDto> findProductListDtos(
            @Param("lastId") Integer lastId,
            @Param("categoryId") Integer categoryId,
            @Param("brandId") Integer brandId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("name") String name);

    @Query("SELECT new com.byteforge.byteforge.dto.response.ProductResponseDto(" +
            "p.id, p.name, p.price, " +
            "p.category.id, p.category.name, " +
            "p.brand.name, p.brand.logoUrl, " +
            "p.warrantyMonths, p.releaseYear, " +
            "p.shortDescription, p.imageUrl, " +
            "sq.quantity) " +
            "FROM Product p " +
            "JOIN p.brand " +
            "JOIN p.category " +
            "LEFT JOIN p.stockQuantity sq " +
            "WHERE p.id = :id")
    ProductResponseDto findProductResponseDtoById(@Param("id") Integer id);
}
