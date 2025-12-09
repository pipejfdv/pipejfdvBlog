package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class InvalidBearerTokenException extends RuntimeException {
    public InvalidBearerTokenException(String token) {
        super("Invalid Bearer Token: " + token);
    }
}
