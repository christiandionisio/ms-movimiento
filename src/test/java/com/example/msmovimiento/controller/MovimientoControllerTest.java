package com.example.msmovimiento.controller;

import com.example.msmovimiento.models.Movimiento;
import com.example.msmovimiento.service.MovimientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(MovimientoController.class)
class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovimientoService service;

    @Test
    void findAllTest() throws Exception {

        Mockito.when(service.findAll()).thenReturn(getListMovimientos());

        mockMvc.perform(MockMvcRequestBuilders.get("/movimientos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].valor").value(new BigDecimal(4000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].valor").value(new BigDecimal(2000)));

        Mockito.verify(service).findAll();

    }

    @Test
    void create() throws Exception {

        Mockito.when(service.create(Mockito.any()))
                .thenReturn(getMovimiento(1L, "CORRIENTE", new BigDecimal(546)));

        mockMvc.perform(MockMvcRequestBuilders.post("/movimientos").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getMovimiento(1L, "CORRIENTE", new BigDecimal(1000)))))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service).create(Mockito.any());

    }


    private List<Movimiento> getListMovimientos() {
        List<Movimiento> movimientoList = new ArrayList<>();

        movimientoList.add(getMovimiento(1L, "CORRIENTE", new BigDecimal(4000)));
        movimientoList.add(getMovimiento(2L, "CORRIENTE", new BigDecimal(2000)));

        return movimientoList;
    }

    private Movimiento getMovimiento(Long id, String tipoMovimiento, BigDecimal valor) {
        Movimiento movimiento = new Movimiento();

        movimiento.setId(id);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(valor);

        return movimiento;
    }

}