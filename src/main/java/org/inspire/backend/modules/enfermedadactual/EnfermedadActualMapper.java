package org.inspire.backend.modules.enfermedadactual;

import org.inspire.backend.modules.enfermedadactual.dto.EnfermedadActualResponse;

class EnfermedadActualMapper {

    private EnfermedadActualMapper() {}

    static EnfermedadActualResponse toResponse(EnfermedadActual e) {
        return new EnfermedadActualResponse(
                e.getId(),
                e.getAtencion().getId(),
                e.getMotivoConsulta(),
                e.getTiempoEnfermedad(),
                e.getSignosSintomas(),
                e.getFuncionesBiologicas(),
                e.getExpectativasTratamiento(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
