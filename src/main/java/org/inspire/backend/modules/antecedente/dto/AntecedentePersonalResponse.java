package org.inspire.backend.modules.antecedente.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AntecedentePersonalResponse(
        UUID id,
        UUID historiaClinicaId,
        String descripcion,
        LocalDate fechaRegistro,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
