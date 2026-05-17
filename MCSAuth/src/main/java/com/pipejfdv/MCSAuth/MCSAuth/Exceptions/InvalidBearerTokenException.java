package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

/*
* Exception thrown when the Authorization header does not contain a valid Bearer token.
*/
public class InvalidBearerTokenException extends RuntimeException {
    public InvalidBearerTokenException(String token) {
        super("Invalid Bearer Token: " + token);
    }
}
