package com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Handlers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Fail.ErrorResponseFail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* Global exception handler that catches custom exceptions
* and returns standardized error responses with appropriate HTTP status codes
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    * Handles IdNotFoundException and returns 404 Not Found
    * @Params ex The IdNotFoundException instance
    * @Return ResponseEntity with error details and HTTP 404 status
    */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponseFail> handleIdNotFoundException(IdNotFoundException ex){
        ErrorResponseFail error = new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /*
    * Handles DuplicateElementException and returns 409 Conflict
    * @Params ex The DuplicateElementException instance
    * @Return ResponseEntity with error details and HTTP 409 status
    */
    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<ErrorResponseFail> handleDuplicateElementException(DuplicateElementException ex){
        ErrorResponseFail error = new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /*
    * Handles NameNotFoundException and returns 400 Bad Request
    * @Params ex The NameNotFoundException instance
    * @Return ResponseEntity with error details and HTTP 400 status
    */
    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<ErrorResponseFail> handleAccountTypeDifferentException(NameNotFoundException ex){
        ErrorResponseFail error = new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
