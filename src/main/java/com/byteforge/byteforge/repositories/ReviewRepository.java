package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.dto.response.ReviewModerationDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductAndActiveTrue(Product product);

    List<Review> findByCustomer(Customer customer);

    Optional<Review> findByProductAndCustomer(Product product, Customer customer);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :productId AND r.active = true")
    Double findAverageRatingByProductId(@Param("productId") Integer productId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.id = :productId AND r.active = true")
    long countByProductIdAndActiveTrue(@Param("productId") Integer productId);

    boolean existsByProductAndCustomer(Product product, Customer customer);

    // Методы для модерации отзывов
    @Query("SELECT new com.byteforge.byteforge.dto.response.ReviewModerationDto(" +
           "r.id, r.userFirstName, r.rating, r.text, r.createdAt, p.name) " +
           "FROM Review r " +
           "JOIN r.product p " +
           "WHERE r.active = false")
    Page<ReviewModerationDto> findByActiveFalseOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.active = false")
    long countByActiveFalse();
} 