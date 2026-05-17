package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

/*
* Exception thrown when a token update operation in the database fails to complete.
*/
public class NotCompletedUpdateTokenException extends RuntimeException {
    public NotCompletedUpdateTokenException() {
        super("Not completed update for token");
    }
}
