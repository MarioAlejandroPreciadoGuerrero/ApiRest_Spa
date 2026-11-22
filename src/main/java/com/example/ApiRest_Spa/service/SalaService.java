package com.example.ApiRest_Spa.service;

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

}
