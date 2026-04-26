package org.inspire.backend.modules.paciente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreatePacienteDto(

        // --- Persona ---
        @NotBlank String tipoDocumentoCodigo,
        @NotBlank String numeroDocumento,
        @NotBlank String nombres,
        @NotBlank String apellidoPaterno,
        String apellidoMaterno,
        @NotNull @Past LocalDate fechaNacimiento,
        @NotBlank String sexoCodigo,
        String celular,
        @Email String email,
        String direccion,

        // --- Paciente ---
        String lugarNacimiento,
        String procedencia,
        String viajesUltimoAnio,
        @NotBlank String gradoInstruccionCodigo,
        String ocupacion,
        @NotBlank String estadoCivilCodigo
) {}
