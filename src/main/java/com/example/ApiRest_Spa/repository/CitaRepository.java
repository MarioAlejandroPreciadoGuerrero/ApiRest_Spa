package com.example.ApiRest_Spa.repository;


import com.example.ApiRest_Spa.ENUM.EstadoCita;
import com.example.ApiRest_Spa.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findCitasByEstado(EstadoCita estado);

    @Query("SELECT c FROM Cita c WHERE c.usuario.idUsuario = :idCliente")
    List<Cita> findCitasByClienteId(@Param("idCliente") Long idCliente);

    //para obtener las citas de u nterapeuta
    @Query("SELECT c FROM Cita c WHERE c.terapeuta.id_terapeuta = :idTerapeuta")
    List<Cita> findCitasByTerapeutaId(@Param("idTerapeuta") Long idTerapeuta);

    @Query("""
            SELECT c FROM Cita c
            WHERE c.usuario.idUsuario = :idCliente
              AND c.estado = :estado
            """)
    List<Cita> findCitasByClienteIdAndEstado(
            @Param("idCliente") Long idCliente,
            @Param("estado") EstadoCita estado
    );

    @Query("""
            SELECT c FROM Cita c
            WHERE c.id_cita = :idCita
              AND c.usuario.idUsuario = :idCliente
            """)
    Optional<Cita> findCitaByIdAndClienteId(
            @Param("idCita") Long idCita,
            @Param("idCliente") Long idCliente
    );
    //para validar si en una sala actual existe una cita que pueda solapar la sala
    //tenga el id de la sala, el estado sea diferente de cancelada
    //
    @Query("""
            SELECT COUNT(c) > 0 FROM Cita c
            WHERE c.sala.id_sala = :idSala
              AND c.estado <> :estadoCancelada
              AND c.fecha_hora_inicio < :fechaHoraFin
              AND c.fecha_hora_fin > :fechaHoraInicio
            """)
    boolean existeCitaQueSolapaSala(
            @Param("idSala") Long idSala,
            @Param("fechaHoraInicio") java.time.LocalDateTime fechaHoraInicio,
            @Param("fechaHoraFin") java.time.LocalDateTime fechaHoraFin,
            @Param("estadoCancelada") EstadoCita estadoCancelada
    );
}
