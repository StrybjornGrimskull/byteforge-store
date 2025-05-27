package com.byteforge.byteforge.services;


import com.byteforge.byteforge.dto.response.ShoppingCartResponseDto;
import com.byteforge.byteforge.entities.Customer;
import com.byteforge.byteforge.entities.Product;


import com.byteforge.byteforge.entities.ShoppingCart;
import com.byteforge.byteforge.repositories.CustomerRepository;
import com.byteforge.byteforge.repositories.ProductRepository;
import com.byteforge.byteforge.repositories.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public List<ShoppingCartResponseDto> getShoppingCartByUsername(String customerEmail) {
        return shoppingCartRepository.findAllProductCustomerByEmail(customerEmail)
                .stream()
                .map(ShoppingCartResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeFromShoppingCart(Integer id, String email) {
        shoppingCartRepository.deleteByProductIdAndCustomerEmail(id, email);
    }

    @Transactional
    public void addToShoppingCart(Integer productId, String customerEmail) {
        // 1. Находим customer по email
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // 2. Находим продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

       ShoppingCart item = new ShoppingCart();
        item.setProduct(product);
        item.setCustomer(customer);
        item.setAddedDate(LocalDateTime.now());

        shoppingCartRepository.save(item);
    }

    public boolean isProductInShoppingCart(Integer productId, String email) {
        return shoppingCartRepository.existsByProductIdAndCustomerEmail(productId, email);
    }
}

