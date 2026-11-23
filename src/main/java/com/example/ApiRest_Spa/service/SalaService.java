package com.example.ApiRest_Spa.service;

import com.example.ApiRest_Spa.dto.SalaResponse;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Sala;
import com.example.ApiRest_Spa.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public Sala obtenerSalaPorId(Long id){
        return salaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada"));
    }

    public List<Sala> obtenerTodas(){
        return salaRepository.findAll();
    }

    public List<Sala> obtenerPorIdSucursal(Long idSucursal){
        return salaRepository.getSalasByIdSucursal(idSucursal);
    }

    public List<Sala> obtenerActivasPorIdSucursal(Long idSucursal){
        return salaRepository.getSalasActivasByIdSucursal(idSucursal);
    }

    public SalaResponse toResponse(Sala s) {
    return SalaResponse.builder()
            .idSala(s.getId_sala())
            .numeroSala(s.getNumero_sala())
            .activa(s.isActiva())
            .idSucursal(s.getSucursal() != null ? s.getSucursal().getId_sucursal() : null)
            .nombreSucursal(s.getSucursal() != null ? s.getSucursal().getNombre() : null)
            .build();
}

}
