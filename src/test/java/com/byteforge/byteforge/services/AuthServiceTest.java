package com.byteforge.byteforge.services;

import com.byteforge.byteforge.dto.LoginRequest;
import com.byteforge.byteforge.utils.JwtUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
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
    private String testEmail;
    private String testPassword;
    private String testJwtToken;

    @BeforeEach
    void setUp() {
        testEmail = "test@example.com";
        testPassword = "password123";
        testJwtToken = "test-jwt-token";
        validLoginRequest = new LoginRequest(testEmail, testPassword);
    }

    @Test
    void login_ShouldSetJwtCookie_WhenAuthenticationSucceeds() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(testEmail);
        when(jwtUtils.generateToken(testEmail)).thenReturn(testJwtToken);
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // Act
        authService.login(validLoginRequest, response);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(testEmail);
        verify(response).addCookie(cookieCaptor.capture());
        
        Cookie jwtCookie = cookieCaptor.getValue();
        assertEquals("jwt", jwtCookie.getName());
        assertEquals(testJwtToken, jwtCookie.getValue());
        assertTrue(jwtCookie.isHttpOnly());
        assertTrue(jwtCookie.getSecure());
        assertEquals("/", jwtCookie.getPath());
        assertEquals(24 * 60 * 60, jwtCookie.getMaxAge());
    }

    @Test
    void login_ShouldThrowResponseStatusException_WhenUserIsDisabled() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new DisabledException("User is disabled"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authService.login(validLoginRequest, response));

        assertEquals(403, exception.getStatusCode().value());
        assertEquals("Email not verified", exception.getReason());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateToken(any());
        verify(response, never()).addCookie(any());
    }

    @Test
    void login_ShouldThrowResponseStatusException_WhenBadCredentials() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authService.login(validLoginRequest, response));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Invalid credentials", exception.getReason());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, never()).generateToken(any());
        verify(response, never()).addCookie(any());
    }

    @Test
    void login_ShouldCreateCorrectAuthenticationToken() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getName()).thenReturn(testEmail);
        when(jwtUtils.generateToken(testEmail)).thenReturn(testJwtToken);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> tokenCaptor = 
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        // Act
        authService.login(validLoginRequest, response);

        // Assert
        verify(authenticationManager).authenticate(tokenCaptor.capture());
        UsernamePasswordAuthenticationToken capturedToken = tokenCaptor.getValue();
        assertEquals(testEmail, capturedToken.getPrincipal());
        assertEquals(testPassword, capturedToken.getCredentials());
    }

    @Test
    void logout_ShouldSetExpiredJwtCookie() {
        // Arrange
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);

        // Act
        authService.logout(response);

        // Assert
        verify(response).addCookie(cookieCaptor.capture());
        Cookie jwtCookie = cookieCaptor.getValue();
        assertEquals("jwt", jwtCookie.getName());
        assertNull(jwtCookie.getValue());
        assertTrue(jwtCookie.isHttpOnly());
        assertTrue(jwtCookie.getSecure());
        assertEquals("/", jwtCookie.getPath());
        assertEquals(0, jwtCookie.getMaxAge());
    }

    @Test
    void login_ShouldThrowBadCredentialsException_WhenEmailIsEmpty() {
        // Arrange
        LoginRequest emptyEmailRequest = new LoginRequest("", testPassword);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authService.login(emptyEmailRequest, response));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Invalid credentials", exception.getReason());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void login_ShouldThrowBadCredentialsException_WhenPasswordIsEmpty() {
        // Arrange
        LoginRequest emptyPasswordRequest = new LoginRequest(testEmail, "");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                authService.login(emptyPasswordRequest, response));

        assertEquals(401, exception.getStatusCode().value());
        assertEquals("Invalid credentials", exception.getReason());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
