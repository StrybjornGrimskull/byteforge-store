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

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public List<ShoppingCartResponseDto> getShoppingCartByUsername(String customerEmail) {
        return shoppingCartRepository.findCartItemsWithProductInfo(customerEmail);
    }

    @Transactional
    public void removeFromShoppingCart(Integer id, String email) {
        shoppingCartRepository.deleteByProductIdAndCustomerEmail(id, email);
    }

    @Transactional
    public void addToShoppingCart(Integer productId, String customerEmail) {
        // 1. Проверяем, есть ли уже такой товар в корзине
        if (shoppingCartRepository.existsByProductIdAndCustomerEmail(productId, customerEmail)) {
            throw new RuntimeException("Товар уже находится в корзине");
        }

        // 2. Находим customer по email
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // 3. Находим продукт
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        // 4. Создаем новую запись в корзине
        ShoppingCart item = new ShoppingCart();
        item.setProduct(product);
        item.setCustomer(customer);
        item.setQuantity(1); // Устанавливаем начальное количество
        item.setAddedDate(LocalDateTime.now());
        shoppingCartRepository.save(item);
    }

    public boolean isProductInShoppingCart(Integer productId, String email) {
        return shoppingCartRepository.existsByProductIdAndCustomerEmail(productId, email);
    }

    @Transactional
    public void updateQuantity(Integer productId, String customerEmail, Integer quantity) {
        // Находим товар в корзине
        ShoppingCart cartItem = shoppingCartRepository.findByProductIdAndCustomerEmail(productId, customerEmail)
                .orElseThrow(() -> new RuntimeException("Товар не найден в корзине"));

        // Проверяем доступное количество на складе
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        // Проверяем, что новое количество допустимо
        if (quantity < 1 || quantity > product.getStockQuantity().getQuantity()) {
            throw new RuntimeException("Недопустимое количество");
        }

        // Обновляем количество
        cartItem.setQuantity(quantity);
        shoppingCartRepository.save(cartItem);
    }
}