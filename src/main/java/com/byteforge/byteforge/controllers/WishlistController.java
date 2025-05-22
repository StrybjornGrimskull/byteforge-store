package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.response.WishlistItemDto;
import com.byteforge.byteforge.services.WishlistService;
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
    public ResponseEntity<List<WishlistItemDto>> getWishlist(Authentication authentication) {
        String username = authentication.getName();
        List<WishlistItemDto> wishlist = wishlistService.getWishlistByUsername(username);
        return ResponseEntity.ok(wishlist);
    }
}
