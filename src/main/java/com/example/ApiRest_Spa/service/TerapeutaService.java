package com.example.ApiRest_Spa.service;

import com.example.ApiRest_Spa.dto.TerapeutaResponse;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.Terapeuta;
import com.example.ApiRest_Spa.repository.CitaRepository;
import com.example.ApiRest_Spa.repository.TerapeutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TerapeutaService {
    @Autowired
    private TerapeutaRepository terapeutaRepository;

    @Autowired
    private CitaRepository citaRepository;

    public TerapeutaService(TerapeutaRepository terapeutaRepository) {
        this.terapeutaRepository = terapeutaRepository;
    }

    public List<Terapeuta> obtenerTodos() {
        return terapeutaRepository.findAll();
    }

    public List<Terapeuta> obtenerPorNombre(String nombre){
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        return this.terapeutaRepository.buscarPorNombre(nombre.trim());
    }

    public Terapeuta obtenerPorId(Long id){
        return this.terapeutaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Terapeuta no encontrado"));
    }

    public List<Terapeuta> obtenerPorServicio(Long idServicio){
        return this.terapeutaRepository.findByServicioId(idServicio);
    }

    public List<Terapeuta> obtenerPorSucursal(Long idSucursal){
        return this.terapeutaRepository.findBySucursalId(idSucursal);
    }

    public List<Terapeuta> obtenerPorServicioYSucursal(Long idServicio, Long idSucursal){
        return this.terapeutaRepository.findByServicioIdAndSucursalId(idServicio, idSucursal);
    }

    public TerapeutaResponse toResponse(Terapeuta t) {
    return TerapeutaResponse.builder()
            .idTerapeuta(t.getId_terapeuta())
            .nombre(t.getUsuario() != null ? t.getUsuario().getNombre() : null)
            .idSucursal(t.getSucursal() != null ? t.getSucursal().getId_sucursal() : null)
            .nombreSucursal(t.getSucursal() != null ? t.getSucursal().getNombre() : null)
            .fechaContratacion(t.getFecha_contratacion())
            .build();
    }

    public boolean tieneCitaEnHora(LocalDateTime horaInicio, Long idTerapeuta){
        List<Cita> citas = citaRepository.findCitasByTerapeutaId(idTerapeuta);
        for(Cita c : citas){
            if(c.getFecha_hora_inicio() == horaInicio){
                return true;
            }
        }
        return false;
    }
}
