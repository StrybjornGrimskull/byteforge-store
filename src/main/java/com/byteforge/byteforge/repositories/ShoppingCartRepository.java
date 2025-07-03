package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.ShoppingCart;
import com.byteforge.byteforge.entities.WishlistItem;
import com.byteforge.byteforge.dto.response.ShoppingCartResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    @Query("SELECT sc FROM ShoppingCart sc JOIN sc.product p JOIN sc.customer c WHERE c.email = :customerEmail")
    List<ShoppingCart> findAllProductCustomerByEmail(String customerEmail);

    @Transactional
    void deleteByProductIdAndCustomerEmail(Integer productId, String email);

    boolean existsByProductIdAndCustomerEmail(Integer productId, String email);

    Optional<ShoppingCart> findByProductIdAndCustomerEmail(Integer productId, String email);

    @Query("SELECT new com.byteforge.byteforge.dto.response.ShoppingCartResponseDto(" +
           "p.imageUrl, p.id, p.name, p.price, sc.quantity, sq.quantity, sc.addedDate) " +
           "FROM ShoppingCart sc " +
           "JOIN sc.product p " +
           "JOIN p.stockQuantity sq " +
           "JOIN sc.customer c " +
           "WHERE c.email = :customerEmail")
    List<ShoppingCartResponseDto> findCartItemsWithProductInfo(@Param("customerEmail") String customerEmail);
}