package com.byteforge.byteforge.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long expiration;

    public JwtUtils(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    // Генерация JWT токена
    public String generateToken(String userDetails) {
        return Jwts.builder()
                .subject(userDetails) // Устанавливаем имя пользователя
                .issuedAt(new Date()) // Время создания
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Время истечения
                .signWith(key) // Алгоритм подписи
                .compact();// Собираем токен
    }

    // Валидация токена
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Дополнительный метод для извлечения имени пользователя
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}