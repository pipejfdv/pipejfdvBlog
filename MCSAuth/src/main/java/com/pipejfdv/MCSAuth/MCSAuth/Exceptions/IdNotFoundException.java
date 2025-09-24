package com.pipejfdv.MCSAuth.MCSAuth.Exceptions;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(UUID id) {
        super("ID " + id + " not found in database - Auth");
    }
}
