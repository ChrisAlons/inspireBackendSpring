package org.inspire.backend.modules.enfermedadactual;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inspire.backend.common.exception.BusinessException;
import org.inspire.backend.common.exception.ResourceNotFoundException;
import org.inspire.backend.modules.atencion.Atencion;
import org.inspire.backend.modules.atencion.AtencionRepository;
import org.inspire.backend.modules.enfermedadactual.dto.CreateEnfermedadActualDto;
import org.inspire.backend.modules.enfermedadactual.dto.EnfermedadActualResponse;
import org.inspire.backend.modules.enfermedadactual.dto.UpdateEnfermedadActualDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
public class EnfermedadActualService {

    private final EnfermedadActualRepository enfermedadRepo;
    private final AtencionRepository atencionRepo;

    @Transactional
    public EnfermedadActualResponse crear(@Valid CreateEnfermedadActualDto dto) {
        Atencion atencion = atencionRepo.findById(dto.atencionId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Atención no encontrada: " + dto.atencionId()));

        if (enfermedadRepo.findByAtencionId(dto.atencionId()).isPresent()) {
            throw new BusinessException(
                    "La atención ya tiene un registro de enfermedad actual");
        }

        EnfermedadActual enfermedad = new EnfermedadActual();
        enfermedad.setAtencion(atencion);
        enfermedad.setMotivoConsulta(dto.motivoConsulta());
        enfermedad.setTiempoEnfermedad(dto.tiempoEnfermedad() != null ? dto.tiempoEnfermedad() : "");
        enfermedad.setSignosSintomas(dto.signosSintomas() != null ? dto.signosSintomas() : "");
        enfermedad.setFuncionesBiologicas(dto.funcionesBiologicas() != null ? dto.funcionesBiologicas() : "");
        enfermedad.setExpectativasTratamiento(dto.expectativasTratamiento() != null ? dto.expectativasTratamiento() : "");

        EnfermedadActual saved = enfermedadRepo.save(enfermedad);
        return EnfermedadActualMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public EnfermedadActualResponse obtenerPorId(UUID id) {
        EnfermedadActual enfermedad = enfermedadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enfermedad actual no encontrada: " + id));
        return EnfermedadActualMapper.toResponse(enfermedad);
    }

    @Transactional(readOnly = true)
    public EnfermedadActualResponse obtenerPorAtencion(UUID atencionId) {
        EnfermedadActual enfermedad = enfermedadRepo.findByAtencionId(atencionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enfermedad actual no encontrada para atención: " + atencionId));
        return EnfermedadActualMapper.toResponse(enfermedad);
    }

    @Transactional
    public EnfermedadActualResponse actualizar(UUID id, UpdateEnfermedadActualDto dto) {
        EnfermedadActual enfermedad = enfermedadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enfermedad actual no encontrada: " + id));

        if (dto.motivoConsulta() != null) enfermedad.setMotivoConsulta(dto.motivoConsulta());
        if (dto.tiempoEnfermedad() != null) enfermedad.setTiempoEnfermedad(dto.tiempoEnfermedad());
        if (dto.signosSintomas() != null) enfermedad.setSignosSintomas(dto.signosSintomas());
        if (dto.funcionesBiologicas() != null) enfermedad.setFuncionesBiologicas(dto.funcionesBiologicas());
        if (dto.expectativasTratamiento() != null) enfermedad.setExpectativasTratamiento(dto.expectativasTratamiento());

        EnfermedadActual updated = enfermedadRepo.save(enfermedad);
        return EnfermedadActualMapper.toResponse(updated);
    }

    @Transactional
    public void eliminar(UUID id) {
        EnfermedadActual enfermedad = enfermedadRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Enfermedad actual no encontrada: " + id));
        enfermedadRepo.delete(enfermedad);
    }
}
