package org.inspire.backend.modules.cita.dto;

import jakarta.validation.constraints.NotNull;
import org.inspire.backend.common.enums.EstadoCita;

public record CambiarEstadoCitaDto(
        @NotNull EstadoCita estado
) {}
