package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.dto.CrearCitaRequest;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.service.CitaService;
import com.example.ApiRest_Spa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private UsuarioService usuarioService;

    //cambie esto para que no se puedan obtener las citas de todos los clientes, solo la tuya
    @GetMapping("/mine")
    public ResponseEntity<List<Cita>> obtenerCitasUsuario(HttpServletRequest request){
        String nombre = (String) request.getAttribute("username");
        Usuario user = usuarioService.obtenerUsuarioPorNombre(nombre);

        return ResponseEntity.ok(citaService.obtenerMisCitas(user.getIdUsuario()));
    }

    @PostMapping
    public Cita crearCita(HttpServletRequest request, @Valid @RequestBody CrearCitaRequest citaRequest){
        String nombre = (String) request.getAttribute("username");
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombre);
        if (usuario == null){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return citaService.agendarCita(citaRequest, usuario.getIdUsuario());
    }

    //necesitamos validar que el que cancela la cita es el mismo cliente que está en la cita
    //si no, un cliente podria cancelar todas las citas que quiera, incluso las de otros clientes
    @PatchMapping("")
    public ResponseEntity<Cita> eliminarCita(HttpServletRequest request,  @RequestBody Cita cita){
        //obtener el usuario de la request
        String email = (String) request.getAttribute("email");
        Usuario user = usuarioService.obtenerUsuarioPorEmail(email);

        //si es su cita, cancelarla
        if(citaService.esMiCita(cita.getId_cita(), user.getIdUsuario())){
            return ResponseEntity.ok(citaService.cancelarCita(cita.getId_cita()));
        }

        return null;
    }
}
