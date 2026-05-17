package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

/*
* Exception thrown when a search by name returns no results
*/
public class NameNotFoundException extends RuntimeException {
    /*
    * Constructs exception with the name that was not found
    * @Params message The name that was searched and not found
    */
    public NameNotFoundException(String message) {
        super("name not found: " + message);
    }
}
