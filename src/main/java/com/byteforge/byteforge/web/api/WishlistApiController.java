package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.response.WishlistItemResponseDto;
import com.byteforge.byteforge.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WishlistApiController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<WishlistItemResponseDto>> getWishlist(Authentication authentication) {
        String email = authentication.getName();
        List<WishlistItemResponseDto> wishlist = wishlistService.getWishlistByUsername(email);
        return ResponseEntity.status(HttpStatus.OK).body(wishlist);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromWishlist(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        wishlistService.removeFromWishlist(productId, email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(
            @RequestParam("productId") Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        wishlistService.addToWishlist(productId, email);
    }

    @GetMapping("/exists/{productId}")
    public ResponseEntity<Boolean> isProductInWishlist(
            @PathVariable Integer productId,
            Authentication authentication) {
        String email = authentication.getName();
        boolean exists = wishlistService.isProductInWishlist(productId, email);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }
}
