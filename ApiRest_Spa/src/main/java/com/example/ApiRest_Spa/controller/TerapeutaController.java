package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.model.Terapeuta;
import com.example.ApiRest_Spa.repository.TerapeutaRepository;
import com.example.ApiRest_Spa.service.TerapeutaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Terapeuta> obtenerTodos(){
        return terapeutaService.obtenerTodos();
    }

    
}
