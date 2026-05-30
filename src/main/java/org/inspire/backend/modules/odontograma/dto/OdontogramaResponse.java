package org.inspire.backend.modules.odontograma.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OdontogramaResponse(
        UUID id,
        UUID historiaClinicaId,
        UUID atencionId,
        OffsetDateTime fecha,
        boolean isInicial,
        String observaciones,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
