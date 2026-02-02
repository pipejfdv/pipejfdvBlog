package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

public class WrongParametersException extends RuntimeException {
    public WrongParametersException() {
        super("Incorrect parameters received");
    }
}
