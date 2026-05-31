package org.inspire.backend.modules.cita.dto;

import org.inspire.backend.common.enums.EstadoCita;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CitaResponse(
        UUID id,
        UUID pacienteId,
        UUID odontologoId,
        UUID historiaClinicaId,
        String pacienteNombres,
        String pacienteApellidoPaterno,
        String pacienteApellidoMaterno,
        String pacienteNumeroDocumento,
        OffsetDateTime fechaHoraInicio,
        OffsetDateTime fechaHoraFin,
        EstadoCita estado,
        String motivo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
