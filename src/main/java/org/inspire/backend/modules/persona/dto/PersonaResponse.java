package org.inspire.backend.modules.persona.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PersonaResponse(
        UUID id,
        String tipoDocumentoCodigo,
        String numeroDocumento,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        LocalDate fechaNacimiento,
        String sexoCodigo,
        String celular,
        String email,
        String direccion,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
