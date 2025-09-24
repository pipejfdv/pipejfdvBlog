package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

import java.util.UUID;

public class TokenNotSavedException extends RuntimeException {
    public TokenNotSavedException(UUID idUser) {
        super("User token not saved: " + idUser);
    }
}
