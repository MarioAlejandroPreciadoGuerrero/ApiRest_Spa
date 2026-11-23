package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.PagoRequest;
import com.example.ApiRest_Spa.dto.PagoResponse;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Pago;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.service.PagoService;
import com.example.ApiRest_Spa.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/mine")
    public ResponseEntity<List<PagoResponse>> verMisPagos(HttpServletRequest request) {
        Usuario usuario = usuarioAutenticado(request);
        List<PagoResponse> pagos = pagoService.obtenerMisPagos(usuario.getIdUsuario()).stream()
                .map(pagoService::toResponse)
                .toList();
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/cita/{idCita}")
    public ResponseEntity<PagoResponse> pagarCita(HttpServletRequest request,
                                                  @PathVariable Long idCita,
                                                  @Valid @RequestBody PagoRequest pagoRequest) {
        Usuario usuario = usuarioAutenticado(request);
        Pago pago = pagoService.realizarPago(idCita, pagoRequest.getIdMetodoPago(), usuario.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.toResponse(pago));
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