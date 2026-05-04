package com.pipejfdv.Juegos.MCSJuegos.Exceptions;

public class ExistElement extends RuntimeException {
    public ExistElement(String message) {
        super("This element already exists: "+ message);
    }
}
