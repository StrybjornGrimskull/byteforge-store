package com.byteforge.byteforge.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.byteforge.byteforge.services.ProfileService;
import com.byteforge.byteforge.dto.response.ProfileResponseDto;

@Controller
public class PageController {
    private final ProfileService profileService;

    public PageController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist.html";
    }

    @GetMapping("/shopping-cart")
    public String shopping_cart() {
        return "shopping-cart.html";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout.html";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
            String email = authentication.getName();
            ProfileResponseDto profile = profileService.getProfileByEmail(email);
            model.addAttribute("user", profile);
        return "profile";
    }
}