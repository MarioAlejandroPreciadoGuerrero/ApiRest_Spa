package com.example.ApiRest_Spa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PagoRequest {
    @NotNull(message = "Debe seleccionar un método de pago")
    @Positive(message = "El método de pago debe ser válido")
    private Long idMetodoPago;
}
