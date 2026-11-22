package com.example.ApiRest_Spa.service;

import com.example.ApiRest_Spa.ENUM.EstadoPago;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Pago;
import com.example.ApiRest_Spa.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
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
}
