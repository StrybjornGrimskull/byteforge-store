package com.byteforge.byteforge.security.handlers;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof DisabledException) {
            // Email не подтверждён
            getRedirectStrategy().sendRedirect(request, response, "/auth/login?error=emailNotVerified");
        } else {
            // Прочие ошибки
            getRedirectStrategy().sendRedirect(request, response, "/auth/login?error=true");
        }
    }
}
