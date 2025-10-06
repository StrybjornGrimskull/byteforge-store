package com.byteforge.byteforge.web.api;

import com.byteforge.byteforge.dto.AuthErrorResponse;
import com.byteforge.byteforge.dto.LoginRequest;
import com.byteforge.byteforge.dto.request.ConsumerRequestDto;
import com.byteforge.byteforge.services.AuthService;
import com.byteforge.byteforge.services.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletResponse response) {
        try {
            authService.login(loginRequest, response);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                // Account disabled (email not verified)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(AuthErrorResponse.disabled(e.getReason()));
            } else if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                // Bad credentials
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(AuthErrorResponse.badCredentials(e.getReason()));
            } else {
                // Generic error
                return ResponseEntity.status(e.getStatusCode())
                        .body(AuthErrorResponse.generic(e.getReason()));
            }
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid ConsumerRequestDto registrationDto) {
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
}
