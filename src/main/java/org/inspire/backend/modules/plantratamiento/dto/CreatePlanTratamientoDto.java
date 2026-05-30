package org.inspire.backend.modules.plantratamiento.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePlanTratamientoDto(
        @NotNull UUID atencionId,
        @NotNull UUID historiaClinicaId,
        String observaciones
) {}
