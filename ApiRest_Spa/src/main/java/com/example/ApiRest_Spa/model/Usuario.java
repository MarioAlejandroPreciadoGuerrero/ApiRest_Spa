package com.example.ApiRest_Spa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column()
    @PastOrPresent(message = "La fecha de registro no puede ser diferente de la fecha actual")
    private Date fechaRegistro;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "El nombre solo puede contener letras")
    private String nombre;

    @Column(nullable = false, length = 50)
    @Email()
    private String email;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9@#]+$")
    private String password;

}
