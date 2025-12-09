package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class TokenInvalidInfo extends RuntimeException {
    public TokenInvalidInfo(String token) {
        super("The token and his information is invalid: " + token);
    }
}
