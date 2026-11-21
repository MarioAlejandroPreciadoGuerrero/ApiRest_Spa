package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/{id}")
    public List<Cita> obtenerCitasUsuario(@PathVariable Long id){
        return citaService.obtenerMisCitas(id);
    }


    @PostMapping
    public Cita crearCita(@RequestBody Cita cita){
        return citaService.agendarCita( cita );
    }

    @PatchMapping("/{id}")
    public Cita eliminarCita(@PathVariable Long id,  @RequestBody Cita cita){
        if (citaService.obtenerCitaPorId(id) != null){
            return citaService.cancelarCita(id);
        }
        return null;
    }
}
