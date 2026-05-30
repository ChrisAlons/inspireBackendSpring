package org.inspire.backend.modules.tratamientoejecutado.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateTratamientoEjecutadoDto(
        @NotNull UUID planDetalleId,
        @NotNull UUID atencionId,
        @NotNull UUID odontologoId,
        OffsetDateTime fechaEjecucion,
        String observaciones
) {}
