package org.inspire.backend.modules.odontograma;

import org.inspire.backend.modules.odontograma.dto.OdontogramaResponse;

class OdontogramaMapper {

    private OdontogramaMapper() {}

    static OdontogramaResponse toResponse(Odontograma o) {
        return new OdontogramaResponse(
                o.getId(),
                o.getHistoriaClinica().getId(),
                o.getAtencion().getId(),
                o.getFecha(),
                o.isInicial(),
                o.getObservaciones(),
                o.getCreatedAt(),
                o.getUpdatedAt()
        );
    }
}
