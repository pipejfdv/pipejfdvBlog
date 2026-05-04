package com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.Fail;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorResponseFail {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponseFail(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
