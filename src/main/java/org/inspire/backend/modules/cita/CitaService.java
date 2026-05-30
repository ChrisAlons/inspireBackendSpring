package org.inspire.backend.modules.cita;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.enums.EstadoCita;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.cita.dto.CitaResponse;
import org.inspire.backend.modules.cita.dto.CreateCitaDto;
import org.inspire.backend.modules.cita.dto.UpdateCitaDto;
import org.inspire.backend.modules.paciente.Paciente;
import org.inspire.backend.modules.paciente.PacienteRepository;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class CitaService {

    private static final Map<EstadoCita, Set<EstadoCita>> TRANSICIONES = Map.of(
            EstadoCita.PROGRAMADA, Set.of(EstadoCita.CONFIRMADA, EstadoCita.CANCELADA),
            EstadoCita.CONFIRMADA, Set.of(EstadoCita.EN_CURSO, EstadoCita.CANCELADA),
            EstadoCita.EN_CURSO, Set.of(EstadoCita.ATENDIDA, EstadoCita.NO_ASISTIO)
    );

    private final CitaRepository citaRepo;
    private final PacienteRepository pacienteRepo;
    private final PersonaRepository personaRepo;

    @Transactional
    public CitaResponse crear(@Valid CreateCitaDto dto) {
        Paciente paciente = pacienteRepo.findById(dto.pacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente no encontrado: " + dto.pacienteId()));

        Persona odontologo = personaRepo.findById(dto.odontologoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontólogo no encontrado: " + dto.odontologoId()));

        if (!dto.fechaHoraFin().isAfter(dto.fechaHoraInicio())) {
            throw new BusinessException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setOdontologo(odontologo);
        cita.setFechaHoraInicio(dto.fechaHoraInicio());
        cita.setFechaHoraFin(dto.fechaHoraFin());
        cita.setMotivo(dto.motivo() != null ? dto.motivo() : "");

        Cita saved = citaRepo.save(cita);
        return CitaMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CitaResponse obtenerPorId(UUID id) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada: " + id));
        return CitaMapper.toResponse(cita);
    }

    @Transactional(readOnly = true)
    public Page<CitaResponse> buscar(UUID pacienteId, Pageable pageable) {
        Page<Cita> page = (pacienteId != null)
                ? citaRepo.findByPacienteId(pacienteId, pageable)
                : citaRepo.findAll(pageable);
        return page.map(CitaMapper::toResponse);
    }

    @Transactional
    public CitaResponse actualizar(UUID id, UpdateCitaDto dto) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada: " + id));

        if (dto.fechaHoraInicio() != null) cita.setFechaHoraInicio(dto.fechaHoraInicio());
        if (dto.fechaHoraFin() != null) cita.setFechaHoraFin(dto.fechaHoraFin());
        if (dto.motivo() != null) cita.setMotivo(dto.motivo());

        if (!cita.getFechaHoraFin().isAfter(cita.getFechaHoraInicio())) {
            throw new BusinessException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        Cita updated = citaRepo.save(cita);
        return CitaMapper.toResponse(updated);
    }

    @Transactional
    public CitaResponse cambiarEstado(UUID id, EstadoCita nuevoEstado) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada: " + id));

        EstadoCita estadoActual = cita.getEstado();
        Set<EstadoCita> permitidos = TRANSICIONES.getOrDefault(estadoActual, Set.of());

        if (!permitidos.contains(nuevoEstado)) {
            throw new BusinessException(
                    "Transición no válida de " + estadoActual + " a " + nuevoEstado);
        }

        cita.setEstado(nuevoEstado);
        Cita updated = citaRepo.save(cita);
        return CitaMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        Cita cita = citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cita no encontrada: " + id));
        citaRepo.delete(cita);
    }
}
