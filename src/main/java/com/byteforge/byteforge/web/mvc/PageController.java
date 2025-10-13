package com.byteforge.byteforge.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Простой контроллер для рендеринга HTML страниц.
 * Вся бизнес-логика обрабатывается через API в AuthApiController.
 */
@Controller
@RequestMapping("/auth")
public class PageController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @GetMapping("/verify-email")
    public String showVerifyEmailPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "verify-email";
    }

    @GetMapping("/resend-verification")
    public String showResendVerificationPage() {
        return "resend-verification";
    }

    @GetMapping("/verify")
    public String showVerificationResultPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "verification-result";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }
}

