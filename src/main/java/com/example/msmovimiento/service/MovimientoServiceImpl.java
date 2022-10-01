package com.example.msmovimiento.service;

import com.example.msmovimiento.models.Movimiento;
import com.example.msmovimiento.repo.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    MovimientoRepository repository;


    @Override
    public List<Movimiento> findAll() {
        return repository.findAll();
    }

    @Override
    public Movimiento findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Movimiento create(Movimiento movimiento) {
        return repository.save(movimiento);
    }

    @Override
    public Movimiento update(Movimiento movimiento) {
        return repository.save(movimiento);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
