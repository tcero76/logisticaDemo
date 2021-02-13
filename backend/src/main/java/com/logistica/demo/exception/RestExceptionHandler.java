package com.logistica.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> RestException(NotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ExceptionRes(new Date(),HttpStatus.NOT_FOUND.toString(),ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> RestException(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionRes(new Date(),HttpStatus.BAD_REQUEST.toString(),ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> RestException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionRes(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.toString(),ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> ResException(InternalAuthenticationServiceException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionRes(new Date(), HttpStatus.UNAUTHORIZED.toString(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> ResException(UnauthorizedHandler ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionRes(new Date(), HttpStatus.UNAUTHORIZED.toString(), ex.toString()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionRes> RestException(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionRes(new Date(),HttpStatus.UNAUTHORIZED.toString(),
                        "Credenciales Incorrectas"));
    }
}
