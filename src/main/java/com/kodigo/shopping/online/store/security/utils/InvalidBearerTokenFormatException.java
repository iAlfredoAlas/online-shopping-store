package com.kodigo.shopping.online.store.security.utils;

public class InvalidBearerTokenFormatException extends RuntimeException {

    public InvalidBearerTokenFormatException() {
    }

    public InvalidBearerTokenFormatException(String message) {
        super(message);
    }
}
