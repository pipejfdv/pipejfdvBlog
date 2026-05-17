package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

/*
* Exception thrown when a requested token is not found in the database.
*/
public class NotFoundTokenException extends RuntimeException {
    public NotFoundTokenException(String token) {
        super("Not found token "+token);
    }
}
