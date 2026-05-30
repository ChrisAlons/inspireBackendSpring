package org.inspire.backend.modules.atencion;

import org.inspire.backend.modules.atencion.dto.AtencionResponse;

class AtencionMapper {

    private AtencionMapper() {}

    static AtencionResponse toResponse(Atencion a) {
        return new AtencionResponse(
                a.getId(),
                a.getCita().getId(),
                a.getHistoriaClinica().getId(),
                a.getOdontologo().getId(),
                a.getFechaInicio(),
                a.getFechaFin(),
                a.getNotas(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }
}
