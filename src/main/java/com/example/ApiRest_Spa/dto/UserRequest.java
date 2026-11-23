package com.example.ApiRest_Spa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
     @NotBlank(message = "El nombre de usuario es obligatorio")
     @Size(min = 3, max = 100, message = "El nombre de usuario debe tener entre 3 y 100 caracteres")
     private String username;

     @NotBlank(message = "El email es obligatorio")
     @Email(message = "El email no tiene un formato valido")
     private String email;

     @NotBlank(message = "La contraseña es obligatoria")
     @Pattern(
          regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_*.?-]).{8,}$",
          message = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un carácter especial"
     )
     private String password;
}
