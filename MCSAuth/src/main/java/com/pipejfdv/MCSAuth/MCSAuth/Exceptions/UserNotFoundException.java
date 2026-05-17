package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

/*
* Exception thrown when a user is not found in the MCSUsersFM database.
* This prevents token assignment to non-existent users.
*/
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User " + username + " not found in database - MCSUserFM");
    }
}
