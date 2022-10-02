package com.example.msmovimiento.exception;

public class SaldoNoDisponibleException extends Exception {

    public SaldoNoDisponibleException() {
        super("Saldo no disponible");
    }
}
