package com.example.msmovimiento.service;

import com.example.msmovimiento.dto.CuentaDto;
import com.example.msmovimiento.exception.SaldoNoDisponibleException;
import com.example.msmovimiento.models.Movimiento;
import com.example.msmovimiento.repo.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    MovimientoRepository repository;

    RestTemplate restTemplate = new RestTemplate();
    @Value("${resource-cliente-url}")
    String resourceUrl;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @Override
    public List<Movimiento> findAll() {
        return repository.findAll();
    }

    @Override
    public Movimiento findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Movimiento create(Movimiento movimiento) throws SaldoNoDisponibleException {
        ResponseEntity<CuentaDto> response = restTemplate.getForEntity(resourceUrl + "/numeroCuenta/{nroCuenta}",
                CuentaDto.class, movimiento.getNumeroCuenta());

        CuentaDto cuentaResponse = response.getBody();

        if (cuentaResponse.getSaldoInicial().compareTo(BigDecimal.ZERO) == 0) {
            throw new SaldoNoDisponibleException();
        }

        if (movimiento.getValor().compareTo(BigDecimal.ZERO) < 0 &&
                cuentaResponse.getSaldoInicial().compareTo(movimiento.getValor().abs()) < 0) {
            throw new SaldoNoDisponibleException();
        }


        movimiento.setTipoMovimiento(cuentaResponse.getTipoCuenta());
        movimiento.setCliente(cuentaResponse.getCliente());
        movimiento.setSaldoInicial(cuentaResponse.getSaldoInicial());
        movimiento.setEstado(cuentaResponse.getEstado());
        movimiento.setSaldoDisponible(cuentaResponse.getSaldoInicial().add(movimiento.getValor()));
        movimiento.setIdCliente(cuentaResponse.getIdCliente());

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

    @Override
    public List<Movimiento> findByIdClienteAndFechaBetween(Long idCliente, String startDate,
                                                              String endDate) {

        LocalDate startDateTime = LocalDate.parse(startDate, FORMATTER);
        LocalDate endDateTime = LocalDate.parse(endDate, FORMATTER);
        return repository.findByIdClienteAndFechaBetween(idCliente, startDateTime.atStartOfDay(),
                endDateTime.atStartOfDay());
    }
}
