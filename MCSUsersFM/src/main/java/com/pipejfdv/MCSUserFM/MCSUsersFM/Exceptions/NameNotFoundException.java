package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException(String message) {
        super("name not found: " + message);
    }
}
