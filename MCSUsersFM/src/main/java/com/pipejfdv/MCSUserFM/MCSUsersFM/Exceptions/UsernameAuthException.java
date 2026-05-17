package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

/*
* Exception thrown when a username is not found during authentication
*/
public class UsernameAuthException extends RuntimeException {
    /*
    * Constructs exception with the username that was not found
    * @Params message The username that was searched and not found
    */
    public UsernameAuthException(String message) {
        super("Username Auth Exception not found: " + message);
    }
}
