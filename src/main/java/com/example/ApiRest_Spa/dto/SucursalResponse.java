package com.example.ApiRest_Spa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalResponse {
    private Long idSucursal;
    private String nombre;
    private String direccion;
    private String colonia;
    private String codigoPostal;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
}
