package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.response.ShoppingCartResponseDto;
import com.byteforge.byteforge.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCartResponseDto>> getShoppingCart(Authentication authentication) {
        String email = authentication.getName();
        List<ShoppingCartResponseDto> shoppingCartByUsername = shoppingCartService.getShoppingCartByUsername(email);
        return ResponseEntity.ok(shoppingCartByUsername);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromShoppingCart(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        shoppingCartService.removeFromShoppingCart(productId, email);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<String> addItem(
            @RequestParam("productId") Integer productId,
            Authentication authentication) {

        try {
            String email = authentication.getName();

           shoppingCartService.addToShoppingCart(productId, email);
            return ResponseEntity.ok("Товар добавлен в вишлист");

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        boolean exists = shoppingCartService.isProductInShoppingCart(productId, email);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{productId}/quantity")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Integer productId,
            @RequestParam Integer quantity,
            Authentication authentication) {
        String email = authentication.getName();
        shoppingCartService.updateQuantity(productId, email, quantity);
        return ResponseEntity.noContent().build();
    }
}
