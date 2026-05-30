package org.inspire.backend.modules.cita.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateCitaDto(
        @NotNull UUID pacienteId,
        @NotNull UUID odontologoId,
        @NotNull OffsetDateTime fechaHoraInicio,
        @NotNull OffsetDateTime fechaHoraFin,
        String motivo
) {}
