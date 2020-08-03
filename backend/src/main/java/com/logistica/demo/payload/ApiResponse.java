package com.logistica.demo.payload;

public class ApiResponse {
    private boolean confirmacon;
    private String message;

    public ApiResponse(boolean confirmacion, String message) {
        this.confirmacon = confirmacion;
        this.message = message;
    }

    public boolean isConfirmacon() {
        return confirmacon;
    }

    public void setConfirmacon(boolean confirmacon) {
        this.confirmacon = confirmacon;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
