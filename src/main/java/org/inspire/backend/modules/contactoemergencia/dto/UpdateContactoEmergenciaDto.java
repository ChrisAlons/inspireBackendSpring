package org.inspire.backend.modules.contactoemergencia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateContactoEmergenciaDto(
        String nombresCompletos,
        String celular,
        String parentescoCodigo,
        Boolean isApoderado,
        @Min(1) @Max(10) Short prioridad
) {}
