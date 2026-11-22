package com.example.ApiRest_Spa.service;


import com.example.ApiRest_Spa.ENUM.EstadoCita;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.Sala;
import com.example.ApiRest_Spa.model.Servicio;
import com.example.ApiRest_Spa.model.Terapeuta;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.dto.CrearCitaRequest;
import com.example.ApiRest_Spa.repository.CitaRepository;
import com.example.ApiRest_Spa.repository.SalaRepository;
import com.example.ApiRest_Spa.repository.ServicioRepository;
import com.example.ApiRest_Spa.repository.TerapeutaRepository;
import com.example.ApiRest_Spa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.time.LocalDateTime;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ServicioRepository servicioRepository;
    private final TerapeutaRepository terapeutaRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository, ServicioRepository servicioRepository,
                       TerapeutaRepository terapeutaRepository, SalaRepository salaRepository,
                       UsuarioRepository usuarioRepository) {
        this.citaRepository = citaRepository;
        this.servicioRepository = servicioRepository;
        this.terapeutaRepository = terapeutaRepository;
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
    }

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

    public Cita agendarCita(CrearCitaRequest request, Long idCliente){
        if (!request.getFechaHoraFin().isAfter(request.getFechaHoraInicio())){
            throw new BusinessRulesException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Usuario usuario = usuarioRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Servicio servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado"));
        Terapeuta terapeuta = terapeutaRepository.findById(request.getIdTerapeuta())
                .orElseThrow(() -> new ResourceNotFoundException("Terapeuta no encontrado"));
        Sala sala = salaRepository.findById(request.getIdSala())
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada"));

        Cita cita = Cita.builder()
                .usuario(usuario)
                .servicio(servicio)
                .terapeuta(terapeuta)
                .sala(sala)
                .fecha_hora_inicio(request.getFechaHoraInicio())
                .fecha_hora_fin(request.getFechaHoraFin())
                .fecha_creada(LocalDateTime.now())
                .precio_total(servicio.getPrecio())
                .estado(EstadoCita.PENDIENTE)
                .build();
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

    public boolean esMiCita(Long idCita, Long idCliente){
        Cita cita = citaRepository.findById(idCita).get();
        if(cita == null){
            throw new ResourceNotFoundException("Cita no encontrada");
        }
        return Objects.equals(cita.getUsuario().getIdUsuario(), idCliente);

    }

}
