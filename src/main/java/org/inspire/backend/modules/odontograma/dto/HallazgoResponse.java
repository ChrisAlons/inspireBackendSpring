package org.inspire.backend.modules.odontograma.dto;

import org.inspire.backend.common.enums.EstadoHallazgo;

import java.time.OffsetDateTime;
import java.util.UUID;

public record HallazgoResponse(
        UUID id,
        UUID odontogramaId,
        Short piezaId,
        String caraCodigo,
        String condicionCodigo,
        EstadoHallazgo estado,
        String notas,
        boolean isRegistradoVoz,
        String transcripcionVoz,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
