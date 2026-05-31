package org.inspire.backend.modules.plantratamiento.dto;

import jakarta.validation.constraints.NotNull;
import org.inspire.backend.common.enums.EstadoPlan;

public record CambiarEstadoPlanDto(
        @NotNull EstadoPlan estado,
        String aceptadoPor,
        String notas
) {}
