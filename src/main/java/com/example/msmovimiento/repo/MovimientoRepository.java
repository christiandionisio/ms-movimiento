package com.example.msmovimiento.repo;

import com.example.msmovimiento.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByIdClienteAndFechaBetween(Long idCliente, LocalDateTime startDate,
                                                       LocalDateTime endDate);
}
