package com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.Fail;

import java.time.LocalDateTime;
/*
* Standard error response wrapper for failed API operations
* Contains error message, HTTP status code and timestamp
*/
public class ErrorResponseFail {
    private String errorMessage;
    private int status;
    private LocalDateTime timestamp;
    /*
    * Constructs an error response with the given details
    * @Params errorMessage Description of the error
    * @Params status HTTP status code
    */
    public ErrorResponseFail(String errorMessage, int status) {
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
