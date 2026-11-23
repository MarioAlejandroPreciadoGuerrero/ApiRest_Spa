package com.example.ApiRest_Spa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerapeutaResponse {
    private Long idTerapeuta;
    private String nombre;
    private Long idSucursal;
    private String nombreSucursal;
    private LocalDate fechaContratacion;
}