package com.pipejfdv.MCSAuth.MCSAuth.View.ResponsesHTTP.FailStructureMessage;

import java.time.LocalDateTime;

/*
* Response wrapper for failed HTTP responses in MCSAuth.
* Contains error message, HTTP status code, and timestamp.
*/
public class UserFMDataFail {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    /*
    * Builds a failure response with the given error message and HTTP status
    * @Param message String the error message
    * @Param status int the HTTP status code
    */
    public UserFMDataFail(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
