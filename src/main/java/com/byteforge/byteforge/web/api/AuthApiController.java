package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.LoginRequest;
import com.byteforge.byteforge.dto.MessageResponse;
import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.services.AuthService;
import com.byteforge.byteforge.services.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest,
                                      HttpServletResponse response) {
        authService.login(loginRequest, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid ConsumerRequestDto registrationDto) {
        customerService.registerNewUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = customerService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/verify")
    public ResponseEntity<MessageResponse> verifyEmail(@RequestParam String token) {
        customerService.verifyEmail(token);
        return ResponseEntity.ok(new MessageResponse("Email verified successfully"));
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponse> resendVerificationEmail(@RequestParam String email) {
        customerService.resendVerificationEmail(email);
        return ResponseEntity.ok(new MessageResponse("Verification email sent successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestParam String email) {
        customerService.generatePasswordResetToken(email);
        return ResponseEntity.ok(new MessageResponse("A password reset link has been sent to your email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestParam String token,
                                                         @RequestParam String password,
                                                         @RequestParam String confirmPassword) {
        customerService.resetPassword(token, password, confirmPassword);
        return ResponseEntity.ok(new MessageResponse("Your password has been reset successfully"));
    }
}
