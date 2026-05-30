package org.inspire.backend.modules.plantratamiento.dto;

import java.math.BigDecimal;

public record UpdatePlanDetalleDto(
        Short cantidad,
        BigDecimal precioUnitario,
        Short orden,
        String notas
) {}
