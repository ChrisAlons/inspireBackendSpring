package org.inspire.backend.modules.historiaclinica.dto;

import java.util.UUID;

public record UpdateHistoriaClinicaDto(
        UUID odontologoId,
        String observacionesGenerales
) {}
