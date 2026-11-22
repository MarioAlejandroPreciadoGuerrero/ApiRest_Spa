package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.model.Sucursal;
import com.example.ApiRest_Spa.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public List<Sucursal> obtenerSucursales(){
        return sucursalService.obtenerTodas();
    }

}
