package org.inspire.backend.modules.plantratamiento.dto;

import org.inspire.backend.common.enums.EstadoDetallePlan;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PlanDetalleResponse(
        UUID id,
        UUID planTratamientoId,
        String procedimientoCodigo,
        Short piezaId,
        String caraCodigo,
        Short cantidad,
        BigDecimal precioUnitario,
        EstadoDetallePlan estado,
        Short orden,
        String notas,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
