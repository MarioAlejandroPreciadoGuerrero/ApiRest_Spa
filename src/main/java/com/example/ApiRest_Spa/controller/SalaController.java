package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ApiRest_Spa.model.Sala;
import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {
    @Autowired
    private SalaService salaService;

    @GetMapping
    public List<Sala> obtenerTodas(){
        return salaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Sala obtenerPorId(@PathVariable Long id){
        return salaService.obtenerSalaPorId(id);
    }

    @GetMapping("/sucursal/{idSucursal}")
    public List<Sala> obtenerPorSucursal(@PathVariable Long idSucursal){
        return salaService.obtenerPorIdSucursal(idSucursal);
    }

    @GetMapping("/sucursal/{idSucursal}/activas")
    public List<Sala> obtenerActivasPorSucursal(@PathVariable Long idSucursal){
        return salaService.obtenerActivasPorIdSucursal(idSucursal);
    }
}
