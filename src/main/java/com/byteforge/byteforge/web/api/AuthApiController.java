package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.LoginRequest;
import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.services.AuthService;
import com.byteforge.byteforge.services.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
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
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody ConsumerRequestDto registrationDto) {
        try {
            customerService.registerNewUser(registrationDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("Email already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
}
