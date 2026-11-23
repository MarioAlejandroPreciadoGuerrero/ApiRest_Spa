package com.example.ApiRest_Spa.controller;

import com.example.ApiRest_Spa.dto.LoginRequest;
import com.example.ApiRest_Spa.dto.LoginResponse;
import com.example.ApiRest_Spa.exception.UnauthorizedException;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.repository.UsuarioRepository;
import com.example.ApiRest_Spa.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByNombre(request.getUsername());

        if (usuario == null) {
            throw new UnauthorizedException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getContrasenia())) {
            throw new UnauthorizedException("Credenciales no coinciden");
        }

        String token = jwtService.generateToken(usuario.getNombre());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}