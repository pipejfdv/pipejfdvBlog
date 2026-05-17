package com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.Handlers;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.UserNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.FailStructureMessage.UserFMDataFail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* Global exception handler that maps exceptions to structured HTTP error responses.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    * Handles UserNotFoundException and returns a 404 NOT_FOUND response
    * @Param ex UserNotFoundException the exception to handle
    * @Return ResponseEntity UserFMDataFail with error details and HTTP 404 status
    */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserFMDataFail> handleIdNotFoundException(UserNotFoundException ex) {
        UserFMDataFail error = new UserFMDataFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
