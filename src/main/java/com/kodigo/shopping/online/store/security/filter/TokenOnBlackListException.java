package com.kodigo.shopping.online.store.security.filter;

public class TokenOnBlackListException extends RuntimeException {

    public TokenOnBlackListException() {
    }

    public TokenOnBlackListException(String message) {
        super(message);
    }
}
