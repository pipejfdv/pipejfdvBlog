package com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK;

import java.time.LocalDateTime;

/*
* Standard success response wrapper for all API endpoints
* Contains message, data payload, HTTP status code and timestamp
*/
public class ApiResponseOK<T> {
    private String message;
    private T data;
    private int status;
    private LocalDateTime timestamp;
    /*
    * Constructs a success response with the given data
    * @Params message Description of the response
    * @Params data Generic payload with the response data
    * @Params status HTTP status code
    */
    public ApiResponseOK(String message, T data, int status) {
        this.message = message;
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
