package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

import java.util.UUID;

public class TokenNotSavedInDBException extends RuntimeException {
    public TokenNotSavedInDBException(UUID idUser) {
        super("User token not saved: " + idUser);
    }
}
