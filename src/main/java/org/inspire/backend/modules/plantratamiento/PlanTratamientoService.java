package org.inspire.backend.modules.plantratamiento;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.enums.EstadoPlan;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.atencion.AtencionRepository;
import org.inspire.backend.modules.historiaclinica.HistoriaClinica;
import org.inspire.backend.modules.historiaclinica.HistoriaClinicaRepository;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.inspire.backend.modules.plantratamiento.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class PlanTratamientoService {

    private static final Map<EstadoPlan, Set<EstadoPlan>> TRANSICIONES = Map.of(
            EstadoPlan.PROPUESTO, Set.of(EstadoPlan.ACEPTADO, EstadoPlan.RECHAZADO),
            EstadoPlan.ACEPTADO, Set.of(EstadoPlan.EN_EJECUCION),
            EstadoPlan.EN_EJECUCION, Set.of(EstadoPlan.COMPLETADO, EstadoPlan.PARCIAL)
    );

    private final PlanTratamientoRepository planRepo;
    private final PlanTratamientoEventoRepository eventoRepo;
    private final AtencionRepository atencionRepo;
    private final HistoriaClinicaRepository historiaRepo;
    private final PersonaRepository personaRepo;

    @Transactional
    public PlanTratamientoResponse crear(@Valid CreatePlanTratamientoDto dto) {
        Atencion atencion = atencionRepo.findById(dto.atencionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + dto.atencionId()));

        HistoriaClinica historia = historiaRepo.findById(dto.historiaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historia clínica no encontrada: " + dto.historiaClinicaId()));

        PlanTratamiento plan = new PlanTratamiento();
        plan.setAtencion(atencion);
        plan.setHistoriaClinica(historia);
        plan.setObservaciones(dto.observaciones() != null ? dto.observaciones() : "");

        PlanTratamiento saved = planRepo.save(plan);
        return PlanTratamientoMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public PlanTratamientoResponse obtenerPorId(UUID id) {
        PlanTratamiento plan = planRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + id));
        return PlanTratamientoMapper.toResponse(plan);
    }

    @Transactional(readOnly = true)
    public List<PlanTratamientoResponse> listarPorHistoria(UUID historiaClinicaId) {
        List<PlanTratamiento> planes = planRepo.findByHistoriaClinicaIdOrderByCreatedAtDesc(historiaClinicaId);
        return planes.stream()
                .map(PlanTratamientoMapper::toResponse)
                .toList();
    }

    @Transactional
    public PlanTratamientoResponse actualizar(UUID id, UpdatePlanTratamientoDto dto) {
        PlanTratamiento plan = planRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + id));

        if (dto.observaciones() != null) plan.setObservaciones(dto.observaciones());
        if (dto.isDocumentoImpreso() != null) plan.setDocumentoImpreso(dto.isDocumentoImpreso());

        PlanTratamiento updated = planRepo.save(plan);
        return PlanTratamientoMapper.toResponse(updated);
    }

    @Transactional
    public PlanTratamientoResponse cambiarEstado(UUID id, CambiarEstadoPlanDto dto, UUID actorPersonaId) {
        PlanTratamiento plan = planRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + id));

        EstadoPlan estadoActual = plan.getEstado();
        Set<EstadoPlan> permitidos = TRANSICIONES.getOrDefault(estadoActual, Set.of());

        if (!permitidos.contains(dto.estado())) {
            throw new BusinessException(
                    "Transición no válida de " + estadoActual + " a " + dto.estado());
        }

        Persona actor = personaRepo.findById(actorPersonaId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Persona no encontrada: " + actorPersonaId));

        plan.setEstado(dto.estado());
        PlanTratamiento updated = planRepo.save(plan);

        PlanTratamientoEvento evento = new PlanTratamientoEvento();
        evento.setPlanTratamiento(updated);
        evento.setEstadoNuevo(dto.estado());
        evento.setActorPersona(actor);
        evento.setAceptadoPor(dto.aceptadoPor() != null ? dto.aceptadoPor() : "");
        evento.setNotas(dto.notas() != null ? dto.notas() : "");
        eventoRepo.save(evento);

        return PlanTratamientoMapper.toResponse(updated);
    }

    @Transactional(readOnly = true)
    public List<PlanEventoResponse> listarEventos(UUID planId) {
        planRepo.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + planId));

        List<PlanTratamientoEvento> eventos = eventoRepo.findByPlanTratamientoIdOrderByCreatedAtAsc(planId);
        return eventos.stream()
                .map(PlanTratamientoEventoMapper::toResponse)
                .toList();
    }

    @Transactional
    public void eliminar(UUID id) {
        PlanTratamiento plan = planRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Plan de tratamiento no encontrado: " + id));
        planRepo.delete(plan);
    }
}
