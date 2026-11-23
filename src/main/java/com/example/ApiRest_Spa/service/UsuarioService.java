package com.example.ApiRest_Spa.service;


import com.example.ApiRest_Spa.dto.UserRequest;
import com.example.ApiRest_Spa.dto.UsuarioResponse;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class UsuarioService  {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public Usuario registrarUsuario(UserRequest request){
        if (usuarioRepository.existsByNombre(request.getUsername())){
            throw new BusinessRulesException("El nombre de usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(request.getUsername());
        nuevoUsuario.setFechaRegistro(Date.from(Instant.now()));
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setContrasenia(passwordEncoder.encode(request.getPassword()));
        return usuarioRepository.save(nuevoUsuario);
    }

    public UsuarioResponse toResponse(Usuario usuario) {
    return UsuarioResponse.builder()
            .idUsuario(usuario.getIdUsuario())
            .nombre(usuario.getNombre())
            .email(usuario.getEmail())
            .fechaRegistro(usuario.getFechaRegistro())
            .build();
}

    public Usuario obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario obtenerUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public Usuario obtenerUsuarioPorNombre(String nombre){
        return usuarioRepository.findByNombre(nombre);
    }




}
