package org.inspire.backend.modules.plantratamiento.dto;

public record UpdatePlanTratamientoDto(
        String observaciones,
        Boolean isDocumentoImpreso
) {}
