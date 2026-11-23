package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.UserRequest;
import com.example.ApiRest_Spa.dto.UsuarioResponse;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("")
    public ResponseEntity<UsuarioResponse> registrarUsuario(@Valid @RequestBody UserRequest request) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.toResponse(nuevoUsuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return ResponseEntity.ok(usuarioService.toResponse(usuario));
    }
}