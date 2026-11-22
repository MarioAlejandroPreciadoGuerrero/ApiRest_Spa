package com.example.ApiRest_Spa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequest {
     @NotBlank(message = "El nombre de usuario es obligatorio")
     private String username;

     @NotBlank(message = "El email es obligatorio")
     @Email(message = "El email no tiene un formato valido")
     private String email;

     @NotBlank(message = "La contraseña es obligatoria")
     @Pattern(regexp = "^[a-zA-Z0-9@#_]+$", message = "La contraseña debe contener letras mayus y minus, al menos un numero y un caracter especial")
     private String password;
}
