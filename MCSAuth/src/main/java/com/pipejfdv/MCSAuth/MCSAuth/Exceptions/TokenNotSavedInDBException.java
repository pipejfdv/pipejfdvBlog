package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

import java.util.UUID;

/*
* Exception thrown when a generated token could not be saved to the database.
*/
public class TokenNotSavedInDBException extends RuntimeException {
    public TokenNotSavedInDBException(UUID idUser) {
        super("User token not saved: " + idUser);
    }
}
