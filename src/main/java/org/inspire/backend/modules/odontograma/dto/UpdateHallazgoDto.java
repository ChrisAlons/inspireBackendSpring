package org.inspire.backend.modules.odontograma.dto;

import org.inspire.backend.common.enums.EstadoHallazgo;

public record UpdateHallazgoDto(
        EstadoHallazgo estado,
        String notas
) {}
