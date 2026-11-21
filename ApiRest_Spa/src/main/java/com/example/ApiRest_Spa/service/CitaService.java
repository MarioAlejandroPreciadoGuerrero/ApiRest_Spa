package com.example.ApiRest_Spa.service;


import com.example.ApiRest_Spa.ENUM.EstadoCita;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> obtenerCitasPorEstado(EstadoCita estado){
        return this.citaRepository.findCitasByEstado(estado);
    }

    public Cita obtenerCitaPorId(Long id){
        return this.citaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
    }

    public List<Cita> obtenerMisCitas(Long idCliente){
        return this.citaRepository.findCitasByClienteId(idCliente);
    }

    public List<Cita> obtenerMisCitasPorEstado(Long idCliente, EstadoCita estado){
        return this.citaRepository.findCitasByClienteIdAndEstado(idCliente, estado);
    }

    public Cita obtenerMiCita(Long idCita, Long idCliente){
        return this.citaRepository.findCitaByIdAndClienteId(idCita, idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
    }

    public Cita agendarCita(Cita cita){
        cita.setEstado(EstadoCita.PENDIENTE);
        return citaRepository.save(cita);
    }

    public Cita cancelarMiCita(Long idCita, Long idCliente){
        Cita cita = obtenerMiCita(idCita, idCliente);
        if (cita.getEstado() != EstadoCita.PENDIENTE && cita.getEstado() != EstadoCita.CONFIRMADA) {
            throw new IllegalStateException("Solo se pueden cancelar citas pendientes o confirmadas");
        }
        cita.setEstado(EstadoCita.CANCELADA);
        return citaRepository.save(cita);
    }
    //quizas este lo quitemos pero depende ustedes si lo necesitan
    public Cita cancelarCita(Long idCita){
        return citaRepository.findById(idCita).map(cita -> {
            cita.setEstado(EstadoCita.CANCELADA);
            return citaRepository.save(cita);
        }).orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
    }
}
