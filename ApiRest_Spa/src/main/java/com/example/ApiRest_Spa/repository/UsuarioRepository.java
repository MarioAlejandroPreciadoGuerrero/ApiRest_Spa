package com.example.ApiRest_Spa.repository;


import com.example.ApiRest_Spa.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);

    public Usuario findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
