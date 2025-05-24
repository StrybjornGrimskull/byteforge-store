package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.request.WishlistItemRequestDto;
import com.byteforge.byteforge.dto.response.WishlistItemResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.entities.WishlistItem;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.WishlistItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistItemRepository wishlistItemRepository;
    private final ProductRepository productRepository;
    private  final CustomerRepository customerRepository;

    public List<WishlistItemResponseDto> getWishlistByUsername(String customerEmail) {
         return wishlistItemRepository.findAllProductCustomerByEmail(customerEmail)
                 .stream()
                 .map(WishlistItemResponseDto::fromEntity)
                 .collect(Collectors.toList());
    }

    @Transactional
    public void removeFromWishlist(Integer id, String email) {
        wishlistItemRepository.deleteByProductIdAndCustomerEmail(id, email);
    }

}
