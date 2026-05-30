package org.inspire.backend.modules.odontograma.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.inspire.backend.common.enums.EstadoHallazgo;

public record CreateHallazgoDto(
        @NotNull Short piezaId,
        @NotBlank String caraCodigo,
        @NotBlank String condicionCodigo,
        EstadoHallazgo estado,
        String notas
) {}
