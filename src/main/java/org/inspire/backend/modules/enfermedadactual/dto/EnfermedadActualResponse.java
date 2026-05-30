package org.inspire.backend.modules.enfermedadactual.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EnfermedadActualResponse(
        UUID id,
        UUID atencionId,
        String motivoConsulta,
        String tiempoEnfermedad,
        String signosSintomas,
        String funcionesBiologicas,
        String expectativasTratamiento,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
