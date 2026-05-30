package org.inspire.backend.modules.paciente.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PacienteResponse(
        UUID id,
        UUID personaId,
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
        String codigoHistoria,
        String lugarNacimiento,
        String procedencia,
        String viajesUltimoAnio,
        String gradoInstruccionCodigo,
        String ocupacion,
        String estadoCivilCodigo,
        boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
