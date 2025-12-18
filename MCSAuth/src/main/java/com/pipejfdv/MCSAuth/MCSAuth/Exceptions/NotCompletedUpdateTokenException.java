package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class NotCompletedUpdateTokenException extends RuntimeException {
    public NotCompletedUpdateTokenException() {
        super("Not completed update for token");
    }
}
