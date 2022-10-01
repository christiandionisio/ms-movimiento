package com.example.msmovimiento.service;

import com.example.msmovimiento.dto.CuentaDto;
import com.example.msmovimiento.models.Movimiento;
import com.example.msmovimiento.repo.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    MovimientoRepository repository;

    RestTemplate restTemplate = new RestTemplate();
    String resourceUrl = "http://localhost:8081/cuentas";


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
        ResponseEntity<CuentaDto> response = restTemplate.getForEntity(resourceUrl + "/numeroCuenta/{nroCuenta}",
                CuentaDto.class, movimiento.getNumeroCuenta());

        CuentaDto cuentaResponse = response.getBody();

        if (cuentaResponse.getSaldoInicial().compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("SALDO NO DISPONIBLE EXCEPTION");
        }

        if (movimiento.getValor().compareTo(BigDecimal.ZERO) < 0 &&
                cuentaResponse.getSaldoInicial().compareTo(movimiento.getValor().abs()) < 0) {
            System.out.println("SALDO NO DISPONIBLE EXCEPTION");
        }


        movimiento.setTipoMovimiento(cuentaResponse.getTipoCuenta());
        movimiento.setCliente(cuentaResponse.getCliente());
        movimiento.setSaldoInicial(cuentaResponse.getSaldoInicial());
        movimiento.setEstado(cuentaResponse.getEstado());
        movimiento.setSaldoDisponible(cuentaResponse.getSaldoInicial().add(movimiento.getValor()));

        cuentaResponse.setSaldoInicial(movimiento.getSaldoDisponible());

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CuentaDto> requestEntity = new HttpEntity<>(cuentaResponse, headers);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestEntity, CuentaDto.class);

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
