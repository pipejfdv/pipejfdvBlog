package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

import java.util.UUID;

/*
* Exception thrown when a search by ID returns no results
*/
public class IdNotFoundException extends RuntimeException{
    /*
    * Constructs exception with the ID that was not found
    * @Params id UUID that was searched and not found
    */
    public IdNotFoundException(UUID id){
        super("element not found "+ id);
    }
}
