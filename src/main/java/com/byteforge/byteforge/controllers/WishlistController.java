package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.request.WishlistItemRequestDto;
import com.byteforge.byteforge.dto.response.WishlistItemResponseDto;
import com.byteforge.byteforge.entities.Product;
import com.byteforge.byteforge.services.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<WishlistItemResponseDto>> getWishlist(Authentication authentication) {
        String email = authentication.getName();
        List<WishlistItemResponseDto> wishlist = wishlistService.getWishlistByUsername(email);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        wishlistService.removeFromWishlist(productId, email);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<String> addItem(
            @RequestParam("productId") Integer productId,
            Authentication authentication) {

        try {
            // Получаем email из аутентификации
            String email = authentication.getName();

            // Передаём в сервис
            wishlistService.addToWishlist(productId, email);
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
        boolean exists = wishlistService.isProductInWishlist(productId, email);
        return ResponseEntity.ok(exists);
    }
}
