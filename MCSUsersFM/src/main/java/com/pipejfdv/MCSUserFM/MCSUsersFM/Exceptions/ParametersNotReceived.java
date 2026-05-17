package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

import java.util.UUID;

/*
* Exception thrown when required parameters are missing or null
*/
public class ParametersNotReceived extends RuntimeException {
    /*
    * Constructs exception with the missing parameter details
    * @Params id UUID that was expected but not received
    * @Params message Name or description of missing parameter
    */
    public ParametersNotReceived(UUID id, String message) {
        super("parameters not received ID: "+ id + " or Name: " + message);
    }
}
