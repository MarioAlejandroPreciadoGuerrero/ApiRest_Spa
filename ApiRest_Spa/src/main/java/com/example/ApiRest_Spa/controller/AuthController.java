package com.example.ApiRest_Spa.controller;


import com.example.ApiRest_Spa.dto.LoginRequest;
import com.example.ApiRest_Spa.dto.LoginResponse;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.repository.UsuarioRepository;
import com.example.ApiRest_Spa.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getUsername());

        if(usuario == null){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("Error","Credenciales no coinciden"));
        }

        String toke = jwtService.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new LoginResponse(toke));
    }


}
