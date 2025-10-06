package com.byteforge.byteforge.dto;

public record AuthErrorResponse(
        String error,
        String message,
        String errorType
) {
    public static AuthErrorResponse disabled(String message) {
        return new AuthErrorResponse("ACCOUNT_DISABLED", message, "disabled");
    }
    
    public static AuthErrorResponse badCredentials(String message) {
        return new AuthErrorResponse("BAD_CREDENTIALS", message, "badCredentials");
    }
    
    public static AuthErrorResponse generic(String message) {
        return new AuthErrorResponse("AUTHENTICATION_FAILED", message, "error");
    }
}
