package com.byteforge.byteforge.exceptions;

public class PasswordCompromisedException extends RuntimeException {
    public PasswordCompromisedException(String message) {
        super(message);
    }
}
