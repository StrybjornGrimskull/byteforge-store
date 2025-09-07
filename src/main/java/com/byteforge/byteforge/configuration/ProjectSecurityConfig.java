package com.byteforge.byteforge.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                // .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) // УБРАТЬ или добавить null-check в фильтр!
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Только HTTP, для продакшена лучше requiresSecure()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin").hasAnyRole("USER", "ADMIN", "MODERATOR")
                        .requestMatchers("/admin/dashboard/**").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/admin/dashboard/reviews").hasAnyRole("ADMIN", "MODERATOR")
                        .requestMatchers("/notices").hasRole("USER")
                        .requestMatchers("/reviews/**", "/api/reviews/**").authenticated()
                        .requestMatchers("/", "/contact", "/error", "/register", "/invalidSession", "/apiLogin").permitAll()
                );
        http.formLogin(withDefaults());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * From Spring Security 6.3 version
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        ByteForgeUsernamePwdAuthenticationProvider authenticationProvider =
                new ByteForgeUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return  providerManager;
    }

}
