package org.inspire.backend.modules.plantratamiento;

import org.inspire.backend.modules.plantratamiento.dto.PlanEventoResponse;

class PlanTratamientoEventoMapper {

    private PlanTratamientoEventoMapper() {}

    static PlanEventoResponse toResponse(PlanTratamientoEvento e) {
        return new PlanEventoResponse(
                e.getId(),
                e.getPlanTratamiento().getId(),
                e.getEstadoNuevo(),
                e.getActorPersona().getId(),
                e.getAceptadoPor(),
                e.getNotas(),
                e.getCreatedAt()
        );
    }
}
