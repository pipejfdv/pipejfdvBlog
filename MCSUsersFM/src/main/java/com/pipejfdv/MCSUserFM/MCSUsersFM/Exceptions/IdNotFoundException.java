package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(UUID id){
        super("element not found "+ id);
    }
}
