package org.inspire.backend.modules.tratamientoejecutado.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TratamientoEjecutadoResponse(
        UUID id,
        UUID planDetalleId,
        UUID atencionId,
        UUID odontologoId,
        OffsetDateTime fechaEjecucion,
        String observaciones,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
