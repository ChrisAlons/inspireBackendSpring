package org.inspire.backend.modules.atencion.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AtencionResponse(
        UUID id,
        UUID citaId,
        UUID historiaClinicaId,
        UUID odontologoId,
        OffsetDateTime fechaInicio,
        OffsetDateTime fechaFin,
        String notas,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
