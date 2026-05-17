package com.pipejfdv.Juegos.MCSJuegos.Exceptions;

import java.util.UUID;

/*
* Exception thrown when a requested entity ID is not found in the database
*/
public class IdNotFound extends RuntimeException {
    public IdNotFound(UUID id) {
        super("Id not found: " + id);
    }
}
