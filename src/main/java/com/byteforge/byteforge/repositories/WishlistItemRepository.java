package com.byteforge.byteforge.repositories;

import com.byteforge.byteforge.entities.WishlistItem;
import com.byteforge.byteforge.dto.response.WishlistItemResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {


    @Transactional
    void deleteByProductIdAndCustomerEmail(Integer productId, String email);

    boolean existsByProductIdAndCustomerEmail(Integer productId, String email);

    @Query("SELECT new com.byteforge.byteforge.dto.response.WishlistItemResponseDto(" +
           "p.imageUrl, p.id, p.name, sq.quantity, wi.addedDate) " +
           "FROM WishlistItem wi " +
           "JOIN wi.product p " +
           "JOIN p.stockQuantity sq " +
           "JOIN wi.customer c " +
           "WHERE c.email = :email")
    List<WishlistItemResponseDto> findWishlistItemsWithProductInfo(String email);
}