package com.example.ApiRest_Spa.repository;

import com.example.ApiRest_Spa.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Long> {
    @Query("SELECT sala FROM Sala sala WHERE sala.sucursal.id_sucursal = :idSucursal")
    List<Sala> getSalasByIdSucursal(@Param("idSucursal") Long idSucursal);

    @Query("SELECT sala FROM Sala sala WHERE sala.sucursal.id_sucursal = :idSucursal AND sala.activa = true")
    List<Sala> getSalasActivasByIdSucursal(@Param("idSucursal") Long idSucursal);
}
