package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

/*
* Exception thrown when the token information is invalid or does not match the expected user data.
*/
public class TokenInvalidInfoException extends RuntimeException {
    public TokenInvalidInfoException(String token) {
        super("The token and his information is invalid: " + token);
    }
}
