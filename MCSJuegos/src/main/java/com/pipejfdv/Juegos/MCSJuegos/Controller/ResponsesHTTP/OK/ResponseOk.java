package com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK;

import lombok.Getter;

import java.time.LocalDateTime;
/*
* Generic success response wrapper for REST endpoints
* Contains message, data payload, HTTP status, and timestamp
*/
@Getter
public class ResponseOk<T> {
    private String message;
    private T data;
    private int status;
    private LocalDateTime timestamp;

    public ResponseOk(String message, T data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
