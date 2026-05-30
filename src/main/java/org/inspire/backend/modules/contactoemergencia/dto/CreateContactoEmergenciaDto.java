package org.inspire.backend.modules.contactoemergencia.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateContactoEmergenciaDto(
        @NotBlank String nombresCompletos,
        @NotBlank String celular,
        @NotBlank String parentescoCodigo,
        Boolean isApoderado,
        @Min(1) @Max(10) Short prioridad
) {}
