package com.example.ApiRest_Spa.dto;

import com.example.ApiRest_Spa.ENUM.EstadoCita;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaResponse {
    private Long idCita;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private EstadoCita estado;
    private Double precioTotal;
    private Long idServicio;
    private String nombreServicio;
    private String categoriaServicio;
    private Integer minutosDuracion;
    private Long idTerapeuta;
    private String nombreTerapeuta;
    private Long idSala;
    private String numeroSala;
}
