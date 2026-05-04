package com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.Handlers;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.Fail.ErrorResponseFail;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.ExistElement;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    // Handle ExistElement
    @ExceptionHandler(ExistElement.class)
    public ResponseEntity<ErrorResponseFail> handleExistElement(ExistElement ex) {
        return new ResponseEntity<>(new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    // Handle IdNotFound
    @ExceptionHandler(IdNotFound.class)
    public ResponseEntity<ErrorResponseFail> handleIdNotFound(IdNotFound ex) {
        return new ResponseEntity<>(new ErrorResponseFail(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }
}
