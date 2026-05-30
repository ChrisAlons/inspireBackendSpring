package org.inspire.backend.modules.atencion.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAtencionDto(
        @NotNull UUID citaId,
        @NotNull UUID historiaClinicaId,
        @NotNull UUID odontologoId,
        String notas
) {}
