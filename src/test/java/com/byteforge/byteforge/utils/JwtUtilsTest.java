package com.byteforge.byteforge.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private String testSecret;
    private String testUsername;

    @BeforeEach
    void setUp() {
        testSecret = "mySuperSecretKeyWithAtLeast512BitsLengthThisIsJustExampleKeyChangeItInProduction!";
        long testExpiration = 86400000L; // 24 hours in milliseconds
        testUsername = "test@example.com";
        
        jwtUtils = new JwtUtils(testSecret, testExpiration);
    }

    @Test
    void generateToken_ShouldGenerateValidToken() {
        // Act
        String token = jwtUtils.generateToken(testUsername);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains(".")); // JWT tokens have 3 parts separated by dots
    }

    @Test
    void generateToken_ShouldGenerateValidTokensForSameUser() {
        // Act - Generate multiple tokens
        String token1 = jwtUtils.generateToken(testUsername);
        String token2 = jwtUtils.generateToken(testUsername);
        String token3 = jwtUtils.generateToken(testUsername);

        // Assert - All tokens should be valid and contain the same username
        assertTrue(jwtUtils.validateToken(token1), "First token should be valid");
        assertTrue(jwtUtils.validateToken(token2), "Second token should be valid");
        assertTrue(jwtUtils.validateToken(token3), "Third token should be valid");
        
        // All should extract the same username
        assertEquals(testUsername, jwtUtils.extractUsername(token1), "First token should contain correct username");
        assertEquals(testUsername, jwtUtils.extractUsername(token2), "Second token should contain correct username");
        assertEquals(testUsername, jwtUtils.extractUsername(token3), "Third token should contain correct username");
        
        // Tokens should have valid JWT structure
        assertTrue(token1.contains("."), "First token should have JWT structure");
        assertTrue(token2.contains("."), "Second token should have JWT structure");
        assertTrue(token3.contains("."), "Third token should have JWT structure");
    }

    @Test
    void generateToken_ShouldGenerateDifferentTokensForDifferentUsers() {
        // Arrange
        String anotherUsername = "another@example.com";

        // Act
        String token1 = jwtUtils.generateToken(testUsername);
        String token2 = jwtUtils.generateToken(anotherUsername);

        // Assert
        assertNotEquals(token1, token2);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        // Arrange
        String token = jwtUtils.generateToken(testUsername);

        // Act
        boolean isValid = jwtUtils.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act
        boolean isValid = jwtUtils.validateToken(invalidToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForEmptyToken() {
        // Act
        boolean isValid = jwtUtils.validateToken("");

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForNullToken() {
        // Act
        boolean isValid = jwtUtils.validateToken(null);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForMalformedToken() {
        // Arrange
        String malformedToken = "not.a.valid.jwt.token";

        // Act
        boolean isValid = jwtUtils.validateToken(malformedToken);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        // Arrange
        String token = jwtUtils.generateToken(testUsername);

        // Act
        String extractedUsername = jwtUtils.extractUsername(token);

        // Assert
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void extractUsername_ShouldReturnDifferentUsernamesForDifferentTokens() {
        // Arrange
        String anotherUsername = "another@example.com";
        String token1 = jwtUtils.generateToken(testUsername);
        String token2 = jwtUtils.generateToken(anotherUsername);

        // Act
        String extractedUsername1 = jwtUtils.extractUsername(token1);
        String extractedUsername2 = jwtUtils.extractUsername(token2);

        // Assert
        assertEquals(testUsername, extractedUsername1);
        assertEquals(anotherUsername, extractedUsername2);
        assertNotEquals(extractedUsername1, extractedUsername2);
    }

    @Test
    void extractUsername_ShouldThrowExceptionForInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertThrows(Exception.class, () -> jwtUtils.extractUsername(invalidToken));
    }

    @Test
    void extractUsername_ShouldThrowExceptionForEmptyToken() {
        // Act & Assert
        assertThrows(Exception.class, () -> jwtUtils.extractUsername(""));
    }

    @Test
    void extractUsername_ShouldThrowExceptionForNullToken() {
        // Act & Assert
        assertThrows(Exception.class, () -> jwtUtils.extractUsername(null));
    }

    @Test
    void generateToken_ShouldHandleSpecialCharactersInUsername() {
        // Arrange
        String specialUsername = "test+user@example.com";

        // Act
        String token = jwtUtils.generateToken(specialUsername);
        String extractedUsername = jwtUtils.extractUsername(token);

        // Assert
        assertNotNull(token);
        assertEquals(specialUsername, extractedUsername);
    }

    @Test
    void generateToken_ShouldHandleLongUsername() {
        // Arrange
        String longUsername = "very.long.username.that.might.cause.issues@verylongdomainname.com";

        // Act
        String token = jwtUtils.generateToken(longUsername);
        String extractedUsername = jwtUtils.extractUsername(token);

        // Assert
        assertNotNull(token);
        assertEquals(longUsername, extractedUsername);
    }

    @Test
    void generateToken_ShouldHandleEmptyUsername() {
        // Act
        String token = jwtUtils.generateToken("");
        
        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
        // Empty username should still create a valid token
        assertTrue(jwtUtils.validateToken(token));
    }

    @Test
    void generateToken_ShouldHandleNullUsername() {
        // Act & Assert
        // JWT library may handle null differently - test what actually happens
        try {
            String token = jwtUtils.generateToken(null);
            // If no exception is thrown, the token should be valid
            assertNotNull(token);
            assertTrue(jwtUtils.validateToken(token));
            // The extracted username should be null
            assertNull(jwtUtils.extractUsername(token));
        } catch (NullPointerException | IllegalArgumentException e) {
            // Both exceptions are acceptable for null username
            assertNotNull(e);
        }
    }

    @Test
    void validateToken_ShouldReturnFalseForExpiredToken() {
        // Arrange - Create JwtUtils with negative expiration (already expired)
        JwtUtils expiredJwtUtils = new JwtUtils(testSecret, -1000L); // Negative expiration means already expired
        String token = expiredJwtUtils.generateToken(testUsername);

        // Act
        boolean isValid = expiredJwtUtils.validateToken(token);

        // Assert
        assertFalse(isValid, "Token with negative expiration should be invalid");
    }

    @Test
    void generateToken_ShouldCreateTokenWithCorrectStructure() {
        // Act
        String token = jwtUtils.generateToken(testUsername);

        // Assert
        String[] parts = token.split("\\.");
        assertEquals(3, parts.length); // JWT has 3 parts: header, payload, signature
        assertFalse(parts[0].isEmpty()); // Header
        assertFalse(parts[1].isEmpty()); // Payload
        assertFalse(parts[2].isEmpty()); // Signature
    }
}
