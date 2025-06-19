package com.byteforge.byteforge.controllers;

import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error, Model model) {
        if ("emailNotVerified".equals(error)) {
            model.addAttribute("showEmailNotVerified", true);
        } else if ("true".equals(error)) {
            model.addAttribute("showAuthError", true);
        }
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(ConsumerRequestDto registrationDto) {
        customerService.registerNewUser(registrationDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token, Model model) {
        try {
            customerService.verifyEmail(token);
            return "redirect:/auth/login?verified";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "verification-error";
        }
    }
}
