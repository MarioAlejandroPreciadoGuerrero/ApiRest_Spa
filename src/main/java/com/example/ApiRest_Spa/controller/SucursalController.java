package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.SucursalResponse;
import com.example.ApiRest_Spa.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public ResponseEntity<List<SucursalResponse>> obtenerSucursales() {
        List<SucursalResponse> sucursales = sucursalService.obtenerTodas().stream()
                .map(sucursalService::toResponse)
                .toList();
        return ResponseEntity.ok(sucursales);
    }
}