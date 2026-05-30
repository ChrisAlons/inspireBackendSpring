package org.inspire.backend.modules.enfermedadactual.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateEnfermedadActualDto(
        @NotNull UUID atencionId,
        @NotBlank String motivoConsulta,
        String tiempoEnfermedad,
        String signosSintomas,
        String funcionesBiologicas,
        String expectativasTratamiento
) {}
