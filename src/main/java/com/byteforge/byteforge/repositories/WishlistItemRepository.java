
package com.byteforge.byteforge.repositories;



import com.byteforge.byteforge.entities.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {

    @Query ("SELECT wi FROM WishlistItem wi JOIN wi.product p JOIN wi.customer c WHERE c.email = :customerEmail")
    List<WishlistItem> findAllProductCustomerByEmail(String customerEmail);

    @Transactional
    void deleteByProductIdAndCustomerEmail(Integer productId, String email);

    boolean existsByProductIdAndCustomerEmail(Integer productId, String email);

}