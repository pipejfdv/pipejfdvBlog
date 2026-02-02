package com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Handlers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Fail.ErrorResponseFail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    *   this class it used for handle error in the controller,
    *   it is depended on type error created, this wraps the error in a "ResponseEntity"
    */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponseFail> handleIdNotFoundException(IdNotFoundException ex){
        ErrorResponseFail error = new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<ErrorResponseFail> handleDuplicateElementException(DuplicateElementException ex){
        ErrorResponseFail error = new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.CONFLICT.value()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /*
    * When the parameters are incorrect
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
