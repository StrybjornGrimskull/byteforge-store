package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.ProductListDto;
import com.byteforge.byteforge.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    String FULL_GRAPH = "Product.full";

    @Query("SELECT new com.byteforge.byteforge.dto.ProductListDto(" +
           "p.id, p.name, p.imageUrl, p.price, b.name, " +
           "COALESCE(sq.quantity, 0)) " +
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

    @Override
    @EntityGraph(value = FULL_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Product> findById(Integer id);
}
