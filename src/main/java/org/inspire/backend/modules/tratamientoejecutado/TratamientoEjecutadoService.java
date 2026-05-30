package org.inspire.backend.modules.tratamientoejecutado;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.enums.EstadoDetallePlan;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.atencion.AtencionRepository;
import org.inspire.backend.modules.persona.Persona;
import org.inspire.backend.modules.persona.PersonaRepository;
import org.inspire.backend.modules.plantratamiento.PlanTratamientoDetalle;
import org.inspire.backend.modules.plantratamiento.PlanTratamientoDetalleRepository;
import org.inspire.backend.modules.tratamientoejecutado.dto.CreateTratamientoEjecutadoDto;
import org.inspire.backend.modules.tratamientoejecutado.dto.TratamientoEjecutadoResponse;
import org.inspire.backend.modules.tratamientoejecutado.dto.UpdateTratamientoEjecutadoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class TratamientoEjecutadoService {

    private final TratamientoEjecutadoRepository tratamientoRepo;
    private final PlanTratamientoDetalleRepository detalleRepo;
    private final AtencionRepository atencionRepo;
    private final PersonaRepository personaRepo;

    @Transactional
    public TratamientoEjecutadoResponse crear(@Valid CreateTratamientoEjecutadoDto dto) {
        PlanTratamientoDetalle detalle = detalleRepo.findById(dto.planDetalleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Detalle de plan no encontrado: " + dto.planDetalleId()));

        Atencion atencion = atencionRepo.findById(dto.atencionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + dto.atencionId()));

        Persona odontologo = personaRepo.findById(dto.odontologoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Odontólogo no encontrado: " + dto.odontologoId()));

        TratamientoEjecutado tratamiento = new TratamientoEjecutado();
        tratamiento.setPlanDetalle(detalle);
        tratamiento.setAtencion(atencion);
        tratamiento.setOdontologo(odontologo);
        tratamiento.setFechaEjecucion(dto.fechaEjecucion() != null ? dto.fechaEjecucion() : OffsetDateTime.now(ZoneOffset.UTC));
        tratamiento.setObservaciones(dto.observaciones() != null ? dto.observaciones() : "");

        TratamientoEjecutado saved = tratamientoRepo.save(tratamiento);

        detalle.setEstado(EstadoDetallePlan.EJECUTADO);
        detalleRepo.save(detalle);

        return TratamientoEjecutadoMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TratamientoEjecutadoResponse obtenerPorId(UUID id) {
        TratamientoEjecutado tratamiento = tratamientoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento ejecutado no encontrado: " + id));
        return TratamientoEjecutadoMapper.toResponse(tratamiento);
    }

    @Transactional(readOnly = true)
    public List<TratamientoEjecutadoResponse> listarPorAtencion(UUID atencionId) {
        List<TratamientoEjecutado> tratamientos = tratamientoRepo.findByAtencionId(atencionId);
        return tratamientos.stream()
                .map(TratamientoEjecutadoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TratamientoEjecutadoResponse> listarPorDetalle(UUID planDetalleId) {
        List<TratamientoEjecutado> tratamientos = tratamientoRepo.findByPlanDetalleId(planDetalleId);
        return tratamientos.stream()
                .map(TratamientoEjecutadoMapper::toResponse)
                .toList();
    }

    @Transactional
    public TratamientoEjecutadoResponse actualizar(UUID id, UpdateTratamientoEjecutadoDto dto) {
        TratamientoEjecutado tratamiento = tratamientoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento ejecutado no encontrado: " + id));

        if (dto.observaciones() != null) tratamiento.setObservaciones(dto.observaciones());

        TratamientoEjecutado updated = tratamientoRepo.save(tratamiento);
        return TratamientoEjecutadoMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        TratamientoEjecutado tratamiento = tratamientoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tratamiento ejecutado no encontrado: " + id));
        tratamientoRepo.delete(tratamiento);
    }
}
