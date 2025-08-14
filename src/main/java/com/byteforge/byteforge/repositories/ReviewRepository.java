package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.Review;
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
} 