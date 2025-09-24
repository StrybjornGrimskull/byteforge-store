package com.byteforge.byteforge.exceptions;

public class LogoUploadException extends RuntimeException {
    
    public LogoUploadException(String message) {
        super(message);
    }
    
    public LogoUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}