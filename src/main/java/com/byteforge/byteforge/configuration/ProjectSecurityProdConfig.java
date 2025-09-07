package com.byteforge.byteforge.configuration;

import com.byteforge.byteforge.filter.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@EnableWebSecurity
@Profile("prod")
@RequiredArgsConstructor
public class ProjectSecurityProdConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Не используем сессии
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) //HTTPS
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/dashboard/**").hasRole("ADMIN")
                        .requestMatchers("/api/customers/**").hasRole("ADMIN")
                        .requestMatchers("/admin/users").hasRole("ADMIN")
                        .requestMatchers("/notices").hasRole("USER")
                        .requestMatchers("/api/brands/**").permitAll()
                        .requestMatchers("/api/specifications/**").permitAll()
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/reviews/**").authenticated()
                        .requestMatchers("/reviews/**").authenticated()
                        .requestMatchers("/api/wishlist/**").authenticated()
                        .requestMatchers("/api/shopping-cart/**").authenticated()
                        .requestMatchers("/shopping-cart/**").authenticated()
                        .requestMatchers("/wishlist/**").authenticated()
                        .requestMatchers("/api/profile/**").authenticated()
                        .requestMatchers("/api/orders/**").authenticated()
                        .requestMatchers("/checkout/**").authenticated()
                        .requestMatchers("/profile").authenticated()
                        .requestMatchers("/orders/history").authenticated()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers(
                                "/",
                                "/contact",
                                "/products/**",
                                "/uploads/**",
                                "/uploads/logo/**",
                                "/brands",
                                "/error",
                                "/auth/**",
                                "api/auth/**"
                        ).permitAll()
                );

       http.exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    if (request.getRequestURI().startsWith("/api/")) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    } else {
                        response.sendRedirect("/auth/login?redirect=" + request.getRequestURI());
                    }
                })
        );
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        ByteForgeProdUsernamePwdAuthenticationProvider authenticationProvider =
                new ByteForgeProdUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

}
