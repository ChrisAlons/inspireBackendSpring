package org.inspire.backend.modules.cita.dto;

import java.time.OffsetDateTime;

public record UpdateCitaDto(
        OffsetDateTime fechaHoraInicio,
        OffsetDateTime fechaHoraFin,
        String motivo
) {}
