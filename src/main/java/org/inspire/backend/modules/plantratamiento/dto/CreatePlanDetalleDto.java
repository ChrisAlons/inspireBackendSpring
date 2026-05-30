package org.inspire.backend.modules.plantratamiento.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePlanDetalleDto(
        @NotBlank String procedimientoCodigo,
        @NotNull Short piezaId,
        @NotBlank String caraCodigo,
        @NotNull @Min(1) Short cantidad,
        @NotNull BigDecimal precioUnitario,
        Short orden,
        String notas
) {}
