package com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions;

public class DuplicateElementException extends RuntimeException {
    public DuplicateElementException(String message) {
        super("Element already exists with value: "+ message);
    }
}
