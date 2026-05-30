package org.inspire.backend.modules.historiaclinica.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record HistoriaClinicaResponse(
        UUID id,
        UUID pacienteId,
        UUID odontologoId,
        LocalDate fechaApertura,
        String observacionesGenerales,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
