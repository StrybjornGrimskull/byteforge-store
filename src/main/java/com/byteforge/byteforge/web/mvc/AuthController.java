package com.byteforge.byteforge.web.mvc;

import com.byteforge.byteforge.constants.ApplicationConstants;
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
    public String showLoginPage(@RequestParam(required = false) String error, @RequestParam(required = false) String message, Model model) {

        if (error != null) {
            if ("disabled".equals(error)) {
                model.addAttribute(ApplicationConstants.LOGIN_ERROR_ATTRIBUTE, message != null ? message : "Your account is not activated. Please check your email and verify your account.");
                model.addAttribute(ApplicationConstants.ERROR_TYPE_ATTRIBUTE, "disabled");
            } else if ("badCredentials".equals(error)) {
                model.addAttribute(ApplicationConstants.LOGIN_ERROR_ATTRIBUTE, message != null ? message : "Invalid email or password. Please try again.");
                model.addAttribute(ApplicationConstants.ERROR_TYPE_ATTRIBUTE, "badCredentials");
            } else {
                model.addAttribute(ApplicationConstants.LOGIN_ERROR_ATTRIBUTE, message != null ? message : "Invalid email or password");
                model.addAttribute(ApplicationConstants.ERROR_TYPE_ATTRIBUTE, ApplicationConstants.ERROR_ATTRIBUTE);
            }
        }
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(ConsumerRequestDto registrationDto, RedirectAttributes redirectAttributes) {
        customerService.registerNewUser(registrationDto);
        redirectAttributes.addAttribute(ApplicationConstants.EMAIL_ATTRIBUTE, registrationDto.email());
        return "redirect:/auth/verify-email";
    }

    @GetMapping("/verify-email")
    public String showVerifyEmailPage(@RequestParam String email, Model model) {
        model.addAttribute(ApplicationConstants.EMAIL_ATTRIBUTE, email);
        return "verify-email";
    }

    @GetMapping("/resend-verification")
    public String showResendVerificationPage() {
        return "resend-verification";
    }

    @PostMapping("/resend-verification")
    public String resendVerificationEmail(@RequestParam String email, Model model) {
        try {
            customerService.resendVerificationEmail(email);
            model.addAttribute(ApplicationConstants.EMAIL_ATTRIBUTE, email);
            model.addAttribute("resent", true);
            model.addAttribute(ApplicationConstants.MESSAGE_ATTRIBUTE, "Verification email sent successfully!");
        } catch (RuntimeException e) {
            model.addAttribute(ApplicationConstants.EMAIL_ATTRIBUTE, email);
            model.addAttribute(ApplicationConstants.ERROR_ATTRIBUTE, "Failed to send verification email: " + e.getMessage());
        }
        return "resend-verification";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token, Model model) {
        try {
            customerService.verifyEmail(token); // Возвращаем Customer
            model.addAttribute("verified", true);
            return "verification-result";
        } catch (RuntimeException e) {
            model.addAttribute("verified", false);
            model.addAttribute(ApplicationConstants.ERROR_ATTRIBUTE, e.getMessage());
            return "verification-result";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            customerService.generatePasswordResetToken(email);
            redirectAttributes.addFlashAttribute(ApplicationConstants.MESSAGE_ATTRIBUTE, "A password reset link has been sent to your email.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ApplicationConstants.ERROR_ATTRIBUTE, e.getMessage());
        }
        return "redirect:/auth/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute(ApplicationConstants.ERROR_ATTRIBUTE, "Passwords do not match.");
            return "redirect:/auth/reset-password?token=" + token;
        }
        try {
            customerService.resetPassword(token, password);
            redirectAttributes.addFlashAttribute(ApplicationConstants.MESSAGE_ATTRIBUTE, "Your password has been reset successfully.");
            return "redirect:/auth/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(ApplicationConstants.ERROR_ATTRIBUTE, e.getMessage());
            return "redirect:/auth/reset-password?token=" + token;
        }
    }
}
