package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.ServicioResponse;
import com.example.ApiRest_Spa.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public ResponseEntity<List<ServicioResponse>> listarServicios() {
        List<ServicioResponse> servicios = servicioService.obtenerTodos().stream()
                .map(servicioService::toResponse)
                .toList();
        return ResponseEntity.ok(servicios);
    }
}