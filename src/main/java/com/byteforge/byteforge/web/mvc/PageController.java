package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.services.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final ProfileService profileService;

    public PageController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/wishlist")
    @PreAuthorize("isAuthenticated()")
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