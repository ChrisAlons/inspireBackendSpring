package org.inspire.backend.modules.cita;

import org.inspire.backend.modules.cita.dto.CitaResponse;

class CitaMapper {

    private CitaMapper() {}

    static CitaResponse toResponse(Cita c) {
        return new CitaResponse(
                c.getId(),
                c.getPaciente().getId(),
                c.getOdontologo().getId(),
                c.getFechaHoraInicio(),
                c.getFechaHoraFin(),
                c.getEstado(),
                c.getMotivo(),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }
}
