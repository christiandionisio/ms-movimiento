package com.example.msmovimiento.repo;

import com.example.msmovimiento.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
