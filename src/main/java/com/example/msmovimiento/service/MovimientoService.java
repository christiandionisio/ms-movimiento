package com.example.msmovimiento.service;

import com.example.msmovimiento.exception.SaldoNoDisponibleException;
import com.example.msmovimiento.models.Movimiento;

import java.util.List;

public interface MovimientoService {

    List<Movimiento> findAll();
    Movimiento findById(Long id);
    Movimiento create(Movimiento movimiento) throws SaldoNoDisponibleException;
    Movimiento update(Movimiento movimiento);
    void delete(Long id);

    List<Movimiento> findByIdClienteAndFechaBetween(Long idCliente, String startDate,
                                                       String endDate);

}
