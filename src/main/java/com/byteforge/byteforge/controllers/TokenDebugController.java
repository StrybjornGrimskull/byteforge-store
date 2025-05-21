package com.byteforge.byteforge.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenDebugController {

    @GetMapping("/debug/token")
    public String getToken(@AuthenticationPrincipal Object principal) {
        if (principal instanceof OidcUser oidcUser) {
            return "ID Token: " + oidcUser.getIdToken().getTokenValue();
        }
        if (principal instanceof Jwt jwt) {
            return "JWT: " + jwt.getTokenValue();
        }
        if (principal instanceof JwtAuthenticationToken token) {
            return "JWT: " + token.getToken().getTokenValue();
        }
        return "Principal type: " + principal.getClass();
    }
}
