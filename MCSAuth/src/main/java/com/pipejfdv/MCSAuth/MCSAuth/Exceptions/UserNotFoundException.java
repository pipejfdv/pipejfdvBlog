package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

public class UserNotFoundException extends RuntimeException {
    /*
    This exception occurs when the user was not found in the database and a token cannot be assigned to that user.
     */
    public UserNotFoundException(String username) {
        super("User " + username + " not found in database - MCSUserFM");
    }
}
