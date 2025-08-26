package com.byteforge.byteforge.filter;

import com.byteforge.byteforge.configuration.ByteForgeUserDetailsService;
import com.byteforge.byteforge.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ByteForgeUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, ByteForgeUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        
        // Пропускаем статические ресурсы и разрешенные пути
        return path.startsWith("/uploads/") ||
               path.startsWith("/css/") ||
               path.startsWith("/js/") ||
               path.startsWith("/favicon.ico") ||
               path.startsWith("/auth/") ||
               path.startsWith("/api/auth/") ||
               path.startsWith("/products/") ||
               path.startsWith("/brands") ||
               path.startsWith("/contact") ||
               path.startsWith("/forgot-password") ||
               path.startsWith("/reset-password") ||
               path.startsWith("/verify-email") ||
               path.startsWith("/resend-verification") ||
               path.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            // 1. Получаем JWT из заголовка ИЛИ из кук
            String jwt = extractJwtFromRequest(request);

            // 2. Если токен не найден - пропускаем
            if (jwt == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 3. Проверяем токен и извлекаем username
            if (jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (UsernameNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found");
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Извлекает JWT токен из:
     * 1. Заголовка Authorization (Bearer token)
     * 2. Куки с именем "jwt"
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        // Из заголовка
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // Из кук
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}