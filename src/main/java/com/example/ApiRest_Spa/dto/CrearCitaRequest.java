package com.example.ApiRest_Spa.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrearCitaRequest {
    @NotNull(message = "Debe seleccionar un servicio")
    @Positive(message = "El servicio debe ser válido")
    private Long idServicio;

    @NotNull(message = "Debe seleccionar un terapeuta")
    @Positive(message = "El terapeuta debe ser válido")
    private Long idTerapeuta;

    @NotNull(message = "Debe seleccionar una sala")
    @Positive(message = "La sala debe ser válida")
    private Long idSala;

    @NotNull(message = "La fecha y hora de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha y hora de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDateTime fechaHoraFin;
}