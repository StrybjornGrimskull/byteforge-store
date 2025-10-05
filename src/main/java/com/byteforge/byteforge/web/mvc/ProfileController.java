package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.dto.response.ProfileResponseDto;
import com.byteforge.byteforge.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public String profile(Model model, Authentication authentication) {
        String email = authentication.getName();
        ProfileResponseDto profile = profileService.getProfileByEmail(email);
        model.addAttribute("user", profile);
        return "profile";
    }
}
