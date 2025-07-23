package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

public class NameAccountNotFoundException extends RuntimeException {
    public NameAccountNotFoundException(String message) {
        super("name not found: " + message);
    }
}
