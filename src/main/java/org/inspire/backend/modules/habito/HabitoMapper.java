package org.inspire.backend.modules.habito;

import org.inspire.backend.modules.habito.dto.HabitoResponse;

class HabitoMapper {

    private HabitoMapper() {}

    static HabitoResponse toResponse(Habito h) {
        return new HabitoResponse(
                h.getId(),
                h.getHistoriaClinica().getId(),
                h.getTipo(),
                h.getFrecuencia(),
                h.getDetalle(),
                h.getCreatedAt(),
                h.getUpdatedAt()
        );
    }
}
