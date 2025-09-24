package com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.Handlers;

import com.pipejfdv.MCSAuth.MCSAuth.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.FailStructureMessage.UserFMDataFail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
    * Handler exception if when query to MCSUserFM return value null
    */
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<UserFMDataFail> handleIdNotFoundException(IdNotFoundException ex) {
        UserFMDataFail error = new UserFMDataFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
