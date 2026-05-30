package org.inspire.backend.modules.habito.dto;

import org.inspire.backend.common.enums.FrecuenciaHabito;

public record UpdateHabitoDto(
        FrecuenciaHabito frecuencia,
        String detalle
) {}
