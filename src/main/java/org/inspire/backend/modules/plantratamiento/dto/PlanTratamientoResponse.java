package org.inspire.backend.modules.plantratamiento.dto;

import org.inspire.backend.common.enums.EstadoPlan;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PlanTratamientoResponse(
        UUID id,
        UUID atencionId,
        UUID historiaClinicaId,
        EstadoPlan estado,
        BigDecimal montoTotal,
        String observaciones,
        boolean isDocumentoImpreso,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
