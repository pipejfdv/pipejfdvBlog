package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Handlers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.Responses.ErrorResponseHTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponseHTTP> handleIdNotFoundException(IdNotFoundException ex){
        ErrorResponseHTTP error = new ErrorResponseHTTP(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<ErrorResponseHTTP> handleDuplicateElementException(DuplicateElementException ex){
        ErrorResponseHTTP error = new ErrorResponseHTTP(
                ex.getMessage(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
