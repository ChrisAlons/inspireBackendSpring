package org.inspire.backend.modules.habito.dto;

import org.inspire.backend.common.enums.FrecuenciaHabito;
import org.inspire.backend.common.enums.TipoHabito;

import java.time.OffsetDateTime;
import java.util.UUID;

public record HabitoResponse(
        UUID id,
        UUID historiaClinicaId,
        TipoHabito tipo,
        FrecuenciaHabito frecuencia,
        String detalle,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
