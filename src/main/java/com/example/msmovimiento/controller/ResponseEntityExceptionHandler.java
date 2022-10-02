package com.example.msmovimiento.controller;

import com.example.msmovimiento.exception.SaldoNoDisponibleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { SaldoNoDisponibleException.class })
    protected ResponseEntity<Object> handleConflict(
            SaldoNoDisponibleException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
