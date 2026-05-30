package org.inspire.backend.modules.historiaclinica.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CreateHistoriaClinicaDto(
        @NotNull UUID pacienteId,
        @NotNull UUID odontologoId,
        LocalDate fechaApertura,
        String observacionesGenerales
) {}
