package com.example.msmovimiento.controller;

import com.example.msmovimiento.exception.SaldoNoDisponibleException;
import com.example.msmovimiento.models.Movimiento;
import com.example.msmovimiento.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService service;

    @GetMapping
    public ResponseEntity<List<Movimiento>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Movimiento> create(@RequestBody Movimiento movimiento) throws SaldoNoDisponibleException {

        Movimiento movimientoDB = service.create(movimiento);

        return ResponseEntity.ok().body(movimientoDB);
    }

    @PutMapping
    public ResponseEntity<Movimiento> update(@RequestBody Movimiento movimiento) {
        return (service.findById(movimiento.getId()) == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok().body(service.update(movimiento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<Movimiento>> generarReporte(@RequestParam Long clienteId,
                                                           @RequestParam String startDate,
                                                           @RequestParam String endDate) {
        return ResponseEntity.ok().body(service.findByIdClienteAndFechaBetween(clienteId, startDate, endDate));
    }

}
