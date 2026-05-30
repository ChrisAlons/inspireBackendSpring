package org.inspire.backend.modules.antecedente.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AntecedenteFamiliarResponse(
        UUID id,
        UUID historiaClinicaId,
        String parentescoCodigo,
        String descripcion,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
