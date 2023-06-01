package com.webApp.exceptions.client;

import com.webApp.exceptions.CustomException;

public class TokenExpiredException extends CustomException {
    public TokenExpiredException() {
        super("Token expired.");
    }
}
