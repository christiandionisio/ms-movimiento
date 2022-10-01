package com.example.msmovimiento.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private String numeroCuenta;
    private String cliente;
    private Boolean estado;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Long idCliente;

}
