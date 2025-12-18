package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class TokenInvalidInfoException extends RuntimeException {
    public TokenInvalidInfoException(String token) {
        super("The token and his information is invalid: " + token);
    }
}
