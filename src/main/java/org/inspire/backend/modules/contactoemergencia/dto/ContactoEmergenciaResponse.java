package org.inspire.backend.modules.contactoemergencia.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ContactoEmergenciaResponse(
        UUID id,
        UUID pacienteId,
        String nombresCompletos,
        String celular,
        String parentescoCodigo,
        boolean isApoderado,
        Short prioridad,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
