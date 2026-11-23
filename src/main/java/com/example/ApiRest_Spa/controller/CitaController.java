package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.CitaResponse;
import com.example.ApiRest_Spa.dto.CrearCitaRequest;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.exception.UnauthorizedException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private TerapeutaService terapeutaService;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/mine")
    public ResponseEntity<List<CitaResponse>> obtenerCitasUsuario(HttpServletRequest request) {
        Usuario usuario = usuarioAutenticado(request);
        List<CitaResponse> citas = citaService.obtenerMisCitas(usuario.getIdUsuario()).stream()
                .map(citaService::toResponse)
                .toList();
        return ResponseEntity.ok(citas);
    }

    @PostMapping
    public ResponseEntity<CitaResponse> crearCita(HttpServletRequest request,
                                                  @Valid @RequestBody CrearCitaRequest citaRequest) {
        Usuario usuario = usuarioAutenticado(request);
        Cita citaCreada = citaService.agendarCita(citaRequest, usuario.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.toResponse(citaCreada));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CitaResponse> cancelarCita(HttpServletRequest request,
                                                     @PathVariable Long id) {
        Usuario usuario = usuarioAutenticado(request);
        if (citaService.esMiCita(id, usuario.getIdUsuario())) {
            Cita citaCancelada = citaService.cancelarMiCita(id, usuario.getIdUsuario());
            return ResponseEntity.ok(citaService.toResponse(citaCancelada));
        }
        throw new UnauthorizedException("No tienes permiso para cancelar esta cita");
    }

    private Usuario usuarioAutenticado(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(username);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return usuario;
    }
}