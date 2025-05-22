
package com.byteforge.byteforge.repositories;


import com.byteforge.byteforge.dto.response.WishlistItemDto;
import com.byteforge.byteforge.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {
   @Query ("SELECT wi FROM WishlistItem wi JOIN wi.product p JOIN wi.customer c WHERE c.email = :customerEmail")
    List<WishlistItem> findAllProductCustomerbyName(String customerEmail);
}