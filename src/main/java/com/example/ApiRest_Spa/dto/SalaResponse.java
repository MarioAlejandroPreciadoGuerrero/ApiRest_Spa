package com.example.ApiRest_Spa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaResponse {
    private Long idSala;
    private String numeroSala;
    private boolean activa;
    private Long idSucursal;
    private String nombreSucursal;
}
