package com.example.ApiRest_Spa.service;

import com.example.ApiRest_Spa.ENUM.EstadoPago;
import com.example.ApiRest_Spa.dto.PagoResponse;
import com.example.ApiRest_Spa.ENUM.EstadoCita;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.MetodoPago;
import com.example.ApiRest_Spa.model.Pago;
import com.example.ApiRest_Spa.repository.CitaRepository;
import com.example.ApiRest_Spa.repository.MetodoPagoRepository;
import com.example.ApiRest_Spa.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public PagoService(PagoRepository pagoRepository, CitaRepository citaRepository,
                       MetodoPagoRepository metodoPagoRepository) {
        this.pagoRepository = pagoRepository;
        this.citaRepository = citaRepository;
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<Pago> obtenerMisPagos(Long idCliente){
        return this.pagoRepository.findPagosByIdCliente(idCliente);
    }

    public List<Pago> obtenerMisPagosPorEstado(Long idCliente, EstadoPago estado){
        return this.pagoRepository.findPagosByIdClienteAndEstado(idCliente, estado);
    }

    public Pago obtenerMiPago(Long idPago, Long idCliente){
        return this.pagoRepository.findPagoByIdAndIdCliente(idPago, idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado"));
    }

    @Transactional
    public Pago realizarPago(Long idCita, Long idMetodoPago, Long idCliente){
        Cita cita = citaRepository.findCitaByIdAndClienteId(idCita, idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));

        if (cita.getEstado() == EstadoCita.CANCELADA){
            throw new BusinessRulesException("No se puede pagar una cita cancelada");
        }
        if (pagoRepository.existsByCitaId(idCita)){
            throw new BusinessRulesException("La cita ya tiene un pago registrado");
        }
        if (cita.getPrecio_total() == null || cita.getPrecio_total() <= 0){
            throw new BusinessRulesException("La cita no tiene un precio válido para realizar el pago");
        }

        MetodoPago metodoPago = metodoPagoRepository.findById(idMetodoPago)
                .orElseThrow(() -> new ResourceNotFoundException("Método de pago no encontrado"));

        Pago pago = Pago.builder()
                .cita(cita)
                .metodoPago(metodoPago)
                .monto(cita.getPrecio_total())
                .fecha_pago(LocalDateTime.now())
                .estado_pago(EstadoPago.PAGADO)
                .build();

        return pagoRepository.save(pago);
    }

    public PagoResponse toResponse(Pago p) {
    return PagoResponse.builder()
            .idPago(p.getId_pago())
            .idCita(p.getCita() != null ? p.getCita().getId_cita() : null)
            .nombreMetodoPago(p.getMetodoPago() != null ? p.getMetodoPago().getNombre() : null)
            .monto(p.getMonto())
            .fechaPago(p.getFecha_pago())
            .estadoPago(p.getEstado_pago())
            .build();
}

}
