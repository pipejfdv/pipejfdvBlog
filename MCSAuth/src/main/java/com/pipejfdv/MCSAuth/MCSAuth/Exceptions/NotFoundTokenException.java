package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class NotFoundTokenException extends RuntimeException {
    public NotFoundTokenException(String token) {
        super("Not found token "+token);
    }
}
