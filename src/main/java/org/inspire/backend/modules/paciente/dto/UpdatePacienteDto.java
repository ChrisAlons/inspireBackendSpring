package org.inspire.backend.modules.paciente.dto;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record UpdatePacienteDto(

        // --- Persona (todos opcionales) ---
        String tipoDocumentoCodigo,
        String numeroDocumento,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        LocalDate fechaNacimiento,
        String sexoCodigo,
        String celular,
        @Email String email,
        String direccion,

        // --- Paciente (todos opcionales) ---
        String lugarNacimiento,
        String procedencia,
        String viajesUltimoAnio,
        String gradoInstruccionCodigo,
        String ocupacion,
        String estadoCivilCodigo
) {}
