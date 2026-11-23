package com.example.ApiRest_Spa.dto;

import com.example.ApiRest_Spa.ENUM.EstadoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponse {
    private Long idPago;
    private Long idCita;
    private String nombreMetodoPago;
    private Double monto;
    private LocalDateTime fechaPago;
    private EstadoPago estadoPago;
}
