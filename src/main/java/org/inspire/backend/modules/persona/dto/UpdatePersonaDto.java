package org.inspire.backend.modules.persona.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record UpdatePersonaDto(
        String tipoDocumentoCodigo,
        String numeroDocumento,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        LocalDate fechaNacimiento,
        String sexoCodigo,
        String celular,
        @Email String email,
        String direccion
) {}
