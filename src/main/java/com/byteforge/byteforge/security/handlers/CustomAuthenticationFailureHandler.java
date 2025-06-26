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
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        String email = request.getParameter("username"); // Получаем email из формы входа

        if (exception instanceof DisabledException) {
            // Перенаправляем сразу на страницу верификации
            getRedirectStrategy().sendRedirect(
                    request,
                    response,
                    "/auth/verify-email?email=" + email
            );
        } else {
            // Остальные ошибки (неверный пароль и т. д.)
            getRedirectStrategy().sendRedirect(
                    request,
                    response,
                    "/auth/login?error=true"
            );
        }
    }
}