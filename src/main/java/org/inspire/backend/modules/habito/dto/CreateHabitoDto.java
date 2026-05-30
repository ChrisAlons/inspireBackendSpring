package org.inspire.backend.modules.habito.dto;

import jakarta.validation.constraints.NotNull;
import org.inspire.backend.common.enums.FrecuenciaHabito;
import org.inspire.backend.common.enums.TipoHabito;

import java.util.UUID;

public record CreateHabitoDto(
        @NotNull UUID historiaClinicaId,
        @NotNull TipoHabito tipo,
        @NotNull FrecuenciaHabito frecuencia,
        String detalle
) {}
