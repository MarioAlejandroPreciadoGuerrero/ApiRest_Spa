package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.TerapeutaResponse;
import com.example.ApiRest_Spa.service.TerapeutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/terapeutas")
public class TerapeutaController {

    @Autowired
    private TerapeutaService terapeutaService;

    @GetMapping
    public ResponseEntity<List<TerapeutaResponse>> obtenerTodos() {
        List<TerapeutaResponse> terapeutas = terapeutaService.obtenerTodos().stream()
                .map(terapeutaService::toResponse)
                .toList();
        return ResponseEntity.ok(terapeutas);
    }
}