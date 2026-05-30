package org.inspire.backend.modules.odontograma.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateHallazgoVozDto(
        @NotBlank String transcripcionVoz,
        @NotEmpty @Valid List<CreateHallazgoDto> hallazgos
) {}
