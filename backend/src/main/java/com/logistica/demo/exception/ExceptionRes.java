package com.logistica.demo.exception;

import java.util.Date;

public class ExceptionRes {


    public ExceptionRes(Date timestamp, String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
    }

    private String error;
    private String message;
    private Date timestamp;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
