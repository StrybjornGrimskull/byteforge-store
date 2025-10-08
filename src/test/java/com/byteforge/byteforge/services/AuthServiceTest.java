package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.LoginRequest;
import com.byteforge.byteforge.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "password123";
    private static final String TEST_JWT_TOKEN = "test.jwt.token";

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest(TEST_EMAIL, TEST_PASSWORD);
        invalidLoginRequest = new LoginRequest("wrong@example.com", "wrongpassword");
    }

    @Test
    void login_SuccessfulAuthentication_ShouldSetJwtCookie() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(jwtUtils.generateToken(TEST_EMAIL)).thenReturn(TEST_JWT_TOKEN);

        // Act
        authService.login(validLoginRequest, response);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(TEST_EMAIL);
        verify(response).addCookie(argThat(cookie ->
                "jwt".equals(cookie.getName()) &&
                        TEST_JWT_TOKEN.equals(cookie.getValue()) &&
                        cookie.isHttpOnly() &&
                        cookie.getSecure() &&
                        "/".equals(cookie.getPath()) &&
                        cookie.getMaxAge() == 24 * 60 * 60
        ));
    }

    @Test
    void login_DisabledUser_ShouldThrowForbiddenException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("Account disabled"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authService.login(validLoginRequest, response));

        assertEquals(403, exception.getStatusCode().value());
        assertEquals("Your account is not activated. Please check your email and verify your account.",
                exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtUtils);
        verify(response, never()).addCookie(any(Cookie.class));
    }

    @Test
    void login_BadCredentials_ShouldThrowUnauthorizedException() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authService.login(invalidLoginRequest, response));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Invalid email or password. Please try again.", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtUtils);
        verify(response, never()).addCookie(any(Cookie.class));
    }

    @Test
    void login_GenericException_ShouldThrowInternalServerError() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> authService.login(validLoginRequest, response));

        assertEquals(500, exception.getStatusCode().value());
        assertEquals("An error occurred during authentication. Please try again.", exception.getReason());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtUtils);
        verify(response, never()).addCookie(any(Cookie.class));
    }

    @Test
    void logout_ShouldSetExpiredJwtCookie() {
        // Act
        authService.logout(response);

        // Assert
        verify(response).addCookie(argThat(cookie ->
                "jwt".equals(cookie.getName()) &&
                        cookie.getValue() == null &&
                        cookie.isHttpOnly() &&
                        cookie.getSecure() &&
                        "/".equals(cookie.getPath()) &&
                        cookie.getMaxAge() == 0
        ));
    }

    @Test
    void login_ShouldUseCorrectAuthenticationToken() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_EMAIL);
        when(jwtUtils.generateToken(TEST_EMAIL)).thenReturn(TEST_JWT_TOKEN);

        // Act
        authService.login(validLoginRequest, response);

        // Assert
        verify(authenticationManager).authenticate(argThat(token ->
                token instanceof UsernamePasswordAuthenticationToken &&
                        TEST_EMAIL.equals(token.getPrincipal()) &&
                        TEST_PASSWORD.equals(token.getCredentials())
        ));
    }
}