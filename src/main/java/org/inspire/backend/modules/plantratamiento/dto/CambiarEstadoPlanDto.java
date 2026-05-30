package org.inspire.backend.modules.plantratamiento.dto;

import jakarta.validation.constraints.NotNull;
import org.inspire.backend.common.enums.EstadoPlan;

import java.util.UUID;

public record CambiarEstadoPlanDto(
        @NotNull EstadoPlan estado,
        @NotNull UUID actorPersonaId,
        String aceptadoPor,
        String notas
) {}
