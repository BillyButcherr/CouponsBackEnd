package com.webApp.exceptions;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {
        super("Token expired.");
    }
}
