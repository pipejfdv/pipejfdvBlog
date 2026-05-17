package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

/*
* Exception thrown when the provided parameters are incorrect or invalid
*/
public class WrongParametersException extends RuntimeException {
    /*
    * Constructs exception with a generic incorrect parameters message
    */
    public WrongParametersException() {
        super("Incorrect parameters received");
    }
}
