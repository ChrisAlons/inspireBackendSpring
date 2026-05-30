package org.inspire.backend.modules.plantratamiento;

import org.inspire.backend.modules.plantratamiento.dto.PlanTratamientoResponse;

class PlanTratamientoMapper {

    private PlanTratamientoMapper() {}

    static PlanTratamientoResponse toResponse(PlanTratamiento p) {
        return new PlanTratamientoResponse(
                p.getId(),
                p.getAtencion().getId(),
                p.getHistoriaClinica().getId(),
                p.getEstado(),
                p.getMontoTotal(),
                p.getObservaciones(),
                p.isDocumentoImpreso(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
