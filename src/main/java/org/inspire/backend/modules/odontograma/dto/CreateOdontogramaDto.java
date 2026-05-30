package org.inspire.backend.modules.odontograma.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOdontogramaDto(
        @NotNull UUID historiaClinicaId,
        @NotNull UUID atencionId,
        Boolean isInicial,
        String observaciones
) {}
