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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
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
    @Transactional
    public void addToWishlist(Integer productId, String customerEmail) {
        // 1. Находим customer по email
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // 2. Находим продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));


        // 4. Сохраняем
        WishlistItem item = new WishlistItem();
        item.setProduct(product);
        item.setCustomer(customer);
        item.setAddedDate(LocalDateTime.now());

        wishlistItemRepository.save(item);
    }
    public boolean isProductInWishlist(Integer productId, String email) {
        return wishlistItemRepository.existsByProductIdAndCustomerEmail(productId, email);
    }
}
