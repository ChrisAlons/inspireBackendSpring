package org.inspire.backend.modules.antecedente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAntecedenteFamiliarDto(
        @NotNull UUID historiaClinicaId,
        @NotBlank String parentescoCodigo,
        @NotBlank String descripcion
) {}
