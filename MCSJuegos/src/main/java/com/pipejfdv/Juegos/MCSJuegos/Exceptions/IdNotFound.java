package com.pipejfdv.Juegos.MCSJuegos.Exceptions;

import java.util.UUID;

public class IdNotFound extends RuntimeException {
    public IdNotFound(UUID id) {
        super("Id not found: " + id);
    }
}
