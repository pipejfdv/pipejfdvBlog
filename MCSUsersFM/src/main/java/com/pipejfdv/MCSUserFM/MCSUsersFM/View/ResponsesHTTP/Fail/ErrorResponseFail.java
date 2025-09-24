package com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Fail;

import java.time.LocalDateTime;
public class ErrorResponseHTTP {
    private String errorMessage;
    private int status;
    private LocalDateTime timestamp;
    /*
     * Structure of responses HTTP with error
     * */
    public ErrorResponseHTTP(String errorMessage, int status) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
