package com.example.msmovimiento.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CuentaDto {
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private Long idCliente;
    private String cliente;

}
