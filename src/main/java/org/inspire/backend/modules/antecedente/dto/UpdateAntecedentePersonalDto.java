package org.inspire.backend.modules.antecedente.dto;

import java.time.LocalDate;

public record UpdateAntecedentePersonalDto(
        String descripcion,
        LocalDate fechaRegistro
) {}
