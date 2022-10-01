package com.example.msmovimiento.service;

import com.example.msmovimiento.models.Movimiento;

import java.util.List;

public interface MovimientoService {

    List<Movimiento> findAll();
    Movimiento findById(Long id);
    Movimiento create(Movimiento movimiento);
    Movimiento update(Movimiento movimiento);
    void delete(Long id);

}
