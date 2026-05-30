package org.inspire.backend.modules.antecedente.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateAntecedentePersonalDto(
        @NotNull UUID historiaClinicaId,
        @NotBlank String descripcion,
        LocalDate fechaRegistro
) {}
