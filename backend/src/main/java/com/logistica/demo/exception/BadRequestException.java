package com.logistica.demo.exception;

import com.logistica.demo.model.Cuenta;
import com.logistica.demo.model.Oditem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(Oditem oditem) {
        super("Sin Stock para : " + oditem.getMaterial());
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Cuenta cuenta) {
        super("Conteo idcuenta: " + cuenta.getIdcuenta() + " ya creado para zona " +
                cuenta.getZona().getNombre() +
                " y pendiente");
    }
}
