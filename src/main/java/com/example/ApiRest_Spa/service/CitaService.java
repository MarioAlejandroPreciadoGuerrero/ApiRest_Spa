package com.example.ApiRest_Spa.service;


import com.example.ApiRest_Spa.ENUM.EstadoCita;
import com.example.ApiRest_Spa.exception.BusinessRulesException;
import com.example.ApiRest_Spa.exception.ResourceNotFoundException;
import com.example.ApiRest_Spa.model.Cita;
import com.example.ApiRest_Spa.model.Sala;
import com.example.ApiRest_Spa.model.Servicio;
import com.example.ApiRest_Spa.model.Terapeuta;
import com.example.ApiRest_Spa.model.Usuario;
import com.example.ApiRest_Spa.dto.CitaResponse;
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
    private TerapeutaService terapeutaService;

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

        //ver si el terapeuta ofrece el servicio solicitado con una función lambda
        boolean ofreceServicio = terapeuta.getServicios() != null
                && terapeuta.getServicios().stream()
                        .anyMatch(servicioTerapeuta ->
                                Objects.equals(servicioTerapeuta.getId(), servicio.getId()));
        if (!ofreceServicio) {
            throw new BusinessRulesException("El terapeuta seleccionado no ofrece el servicio solicitado");
        }
        //tambien ver que el terapeuta pertenece a la sucursal donde esta la sala solicitada
        if (terapeuta.getSucursal() == null
                || sala.getSucursal() == null
                || !Objects.equals(
                        terapeuta.getSucursal().getId_sucursal(),
                        sala.getSucursal().getId_sucursal())) {
            throw new BusinessRulesException(
                    "El terapeuta debe pertenecer a la sucursal correspondiente a la sala seleccionada");
        }
        if(terapeutaService.tieneCitaEnHora(request.getFechaHoraInicio(), request.getIdTerapeuta())){
            throw new BusinessRulesException("El terapeuta tiene otra cita en esa hora");
        }
        //validar qe no haya una cita en esa sala a esa hora
        if (citaRepository.existeCitaQueSolapaSala(
                sala.getId_sala(),
                request.getFechaHoraInicio(),
                request.getFechaHoraFin(),
                EstadoCita.CANCELADA)) {
            throw new BusinessRulesException("La sala ya tiene una cita en el horario solicitado");
        }

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
        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada"));
        return Objects.equals(cita.getUsuario().getIdUsuario(), idCliente);
    }

    public CitaResponse toResponse(Cita c) {
    return CitaResponse.builder()
            .idCita(c.getId_cita())
            .fechaHoraInicio(c.getFecha_hora_inicio())
            .fechaHoraFin(c.getFecha_hora_fin())
            .estado(c.getEstado())
            .precioTotal(c.getPrecio_total())
            .idServicio(c.getServicio() != null ? c.getServicio().getId() : null)
            .nombreServicio(c.getServicio() != null ? c.getServicio().getNombre() : null)
            .categoriaServicio(c.getServicio() != null ? c.getServicio().getCategoria() : null)
            .duracion(c.getServicio() != null ? c.getServicio().getDuracion() : null)
            .idTerapeuta(c.getTerapeuta() != null ? c.getTerapeuta().getId_terapeuta() : null)
            .nombreTerapeuta(c.getTerapeuta() != null && c.getTerapeuta().getUsuario() != null
                    ? c.getTerapeuta().getUsuario().getNombre() : null)
            .idSala(c.getSala() != null ? c.getSala().getId_sala() : null)
            .numeroSala(c.getSala() != null ? c.getSala().getNumero_sala() : null)
            .build();
    }

    public List<Cita> getCitasByTerapeuta(Long idTerapeuta){
        return this.citaRepository.findCitasByTerapeutaId(idTerapeuta);
    }

}
