package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

import java.util.UUID;

public class ParametersNotReceived extends RuntimeException {
    public ParametersNotReceived(UUID id, String message) {
        super("parameters not received ID: "+ id + " or Name: " + message);
    }
}
