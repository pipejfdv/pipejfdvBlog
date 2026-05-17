package com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.Handlers;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.Fail.ErrorResponseFail;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.ExistElement;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
* Global exception handler for REST controllers
* Catches custom exceptions and returns structured error responses
*/
@RestControllerAdvice
public class RequestExceptionHandler {
    /*
	* Handles ExistElement exception when a duplicate element is found
	* @Param ex ExistElement exception instance
	* @Return ResponseEntity with CONFLICT status and error details
	*/
    @ExceptionHandler(ExistElement.class)
    public ResponseEntity<ErrorResponseFail> handleExistElement(ExistElement ex) {
        return new ResponseEntity<>(new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    /*
	* Handles IdNotFound exception when a requested ID does not exist
	* @Param ex IdNotFound exception instance
	* @Return ResponseEntity with NOT_FOUND status and error details
	*/
    @ExceptionHandler(IdNotFound.class)
    public ResponseEntity<ErrorResponseFail> handleIdNotFound(IdNotFound ex) {
        return new ResponseEntity<>(new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }
}
