package org.inspire.backend.modules.historiaclinica;

import org.inspire.backend.modules.historiaclinica.dto.HistoriaClinicaResponse;

class HistoriaClinicaMapper {

    private HistoriaClinicaMapper() {}

    static HistoriaClinicaResponse toResponse(HistoriaClinica h) {
        return new HistoriaClinicaResponse(
                h.getId(),
                h.getPaciente().getId(),
                h.getOdontologo().getId(),
                h.getFechaApertura(),
                h.getObservacionesGenerales(),
                h.getCreatedAt(),
                h.getUpdatedAt()
        );
    }
}
