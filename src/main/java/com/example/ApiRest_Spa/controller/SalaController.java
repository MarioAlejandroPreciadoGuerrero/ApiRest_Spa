package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.SalaResponse;
import com.example.ApiRest_Spa.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public ResponseEntity<List<SalaResponse>> obtenerTodas() {
        List<SalaResponse> salas = salaService.obtenerTodas().stream()
                .map(salaService::toResponse)
                .toList();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.toResponse(salaService.obtenerSalaPorId(id)));
    }

    @GetMapping("/sucursal/{idSucursal}")
    public ResponseEntity<List<SalaResponse>> obtenerPorSucursal(@PathVariable Long idSucursal) {
        List<SalaResponse> salas = salaService.obtenerPorIdSucursal(idSucursal).stream()
                .map(salaService::toResponse)
                .toList();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/sucursal/{idSucursal}/activas")
    public ResponseEntity<List<SalaResponse>> obtenerActivasPorSucursal(@PathVariable Long idSucursal) {
        List<SalaResponse> salas = salaService.obtenerActivasPorIdSucursal(idSucursal).stream()
                .map(salaService::toResponse)
                .toList();
        return ResponseEntity.ok(salas);
    }
}