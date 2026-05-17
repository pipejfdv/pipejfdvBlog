package com.pipejfdv.Juegos.MCSJuegos.Exceptions;

/*
* Exception thrown when trying to create an element that already exists
*/
public class ExistElement extends RuntimeException {
    public ExistElement(String message) {
        super("This element already exists: "+ message);
    }
}
