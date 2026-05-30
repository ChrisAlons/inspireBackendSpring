package org.inspire.backend.modules.cita.dto;

import org.inspire.backend.common.enums.EstadoCita;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CitaResponse(
        UUID id,
        UUID pacienteId,
        UUID odontologoId,
        OffsetDateTime fechaHoraInicio,
        OffsetDateTime fechaHoraFin,
        EstadoCita estado,
        String motivo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
