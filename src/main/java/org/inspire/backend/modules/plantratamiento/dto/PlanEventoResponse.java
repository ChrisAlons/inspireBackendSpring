package org.inspire.backend.modules.plantratamiento.dto;

import org.inspire.backend.common.enums.EstadoPlan;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PlanEventoResponse(
        UUID id,
        UUID planTratamientoId,
        EstadoPlan estadoNuevo,
        UUID actorPersonaId,
        String aceptadoPor,
        String notas,
        OffsetDateTime createdAt
) {}
