package com.example.ApiRest_Spa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioResponse {
    private Long idServicio;
    private String nombre;
    private Integer minutosDuracion;
    private String descripcion;
    private String categoria;
    private Double precio;
}
