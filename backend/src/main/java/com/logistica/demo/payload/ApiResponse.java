package com.logistica.demo.payload;

public class ApiResponse {
    private boolean confirmacion;
    private String message;

    public ApiResponse(boolean confirmacion, String message) {
        this.confirmacion = confirmacion;
        this.message = message;
    }

    public boolean isConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
