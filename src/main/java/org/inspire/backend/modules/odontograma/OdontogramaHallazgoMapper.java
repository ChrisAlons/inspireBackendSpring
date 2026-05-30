package org.inspire.backend.modules.odontograma;

import org.inspire.backend.modules.odontograma.dto.HallazgoResponse;

class OdontogramaHallazgoMapper {

    private OdontogramaHallazgoMapper() {}

    static HallazgoResponse toResponse(OdontogramaHallazgo h) {
        return new HallazgoResponse(
                h.getId(),
                h.getOdontograma().getId(),
                h.getPieza().getId(),
                h.getCara().getCodigo(),
                h.getCondicion().getCodigo(),
                h.getEstado(),
                h.getNotas(),
                h.isRegistradoVoz(),
                h.getTranscripcionVoz(),
                h.getCreatedAt(),
                h.getUpdatedAt()
        );
    }
}
