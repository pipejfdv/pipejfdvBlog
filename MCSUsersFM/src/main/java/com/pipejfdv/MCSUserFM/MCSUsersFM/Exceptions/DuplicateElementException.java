package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

/*
* Exception thrown when trying to create an element that already exists
*/
public class DuplicateElementException extends RuntimeException {
    /*
    * Constructs exception with the duplicate element value
    * @Params message The value that caused the duplicate
    */
    public DuplicateElementException(String message) {
        super("Element already exists with value: "+ message);
    }
}
