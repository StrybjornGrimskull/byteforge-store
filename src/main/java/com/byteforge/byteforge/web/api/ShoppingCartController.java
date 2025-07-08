package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.ShoppingCartResponseDto;
import com.byteforge.byteforge.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<List<ShoppingCartResponseDto>> getShoppingCart(Authentication authentication) {
        String email = authentication.getName();
        List<ShoppingCartResponseDto> shoppingCartByUsername = shoppingCartService.getShoppingCartByUsername(email);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartByUsername);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromShoppingCart(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        shoppingCartService.removeFromShoppingCart(productId, email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(
            @RequestParam("productId") Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        shoppingCartService.addToShoppingCart(productId, email);
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        boolean exists = shoppingCartService.isProductInShoppingCart(productId, email);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

    @PutMapping("/{productId}/quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuantity(
            @PathVariable Integer productId,
            @RequestParam Integer quantity,
            Authentication authentication) {
        String email = authentication.getName();
        shoppingCartService.updateQuantity(productId, email, quantity);
    }
}
