package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

public class UsernameAuthException extends RuntimeException {
    public UsernameAuthException(String message) {
        super("Username Auth Exception not found: " + message);
    }
}
